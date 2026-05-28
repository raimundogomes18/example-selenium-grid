# Selenium Grid no Kubernetes — Guia de Migração Corporativa

Este guia descreve como levar o Selenium Grid de Docker Compose para um cluster Kubernetes corporativo, aproveitando a arquitetura **fully distributed** do Grid 4 (os mesmos componentes do diretório `full-grid/`) para obter escalabilidade, resiliência e integração com pipelines CI/CD.

> **Referência oficial:** [selenium.dev/documentation/grid](https://www.selenium.dev/documentation/grid/)

---

## Sumário

- [Por que Kubernetes?](#por-que-kubernetes)
- [Arquitetura dos componentes no Kubernetes](#arquitetura-dos-componentes-no-kubernetes)
- [Pré-requisitos](#pré-requisitos)
- [Estrutura dos manifests](#estrutura-dos-manifests)
- [Passo a passo de implantação](#passo-a-passo-de-implantação)
  - [1. Namespace e ConfigMap](#1-namespace-e-configmap)
  - [2. Event Bus](#2-event-bus)
  - [3. Session Map, Session Queue e Distributor](#3-session-map-session-queue-e-distributor)
  - [4. Router (ponto de entrada)](#4-router-ponto-de-entrada)
  - [5. Nodes de browser](#5-nodes-de-browser)
  - [6. Autoscaling com HPA](#6-autoscaling-com-hpa)
  - [7. Ingress corporativo](#7-ingress-corporativo)
- [Executando os testes Maven no cluster](#executando-os-testes-maven-no-cluster)
- [Boas práticas corporativas](#boas-práticas-corporativas)
- [Troubleshooting](#troubleshooting)
- [Referências](#referências)

---

## Por que Kubernetes?

| Docker Compose | Kubernetes |
|---|---|
| Escala manual (`--scale`) | Escala automática via HPA |
| Um único host | Multi-node, multi-zona |
| Restart manual em falha | Self-healing automático |
| Sem controle de recursos | Requests/Limits por container |
| Sem isolamento de equipes | Namespaces por projeto/squad |
| Sem integração com registry corporativo | imagePullSecrets + ServiceAccount |

---

## Arquitetura dos componentes no Kubernetes

O Selenium Grid 4 no modo **fully distributed** mapeia naturalmente para Deployments Kubernetes:

```
                        ┌─────────────────────────────────────────┐
                        │         Namespace: selenium-grid         │
                        │                                          │
  Cliente / CI ──────►  │  Ingress ──► Service(Router) ──► Router  │
                        │                    │                     │
                        │            Session Map  Session Queue    │
                        │                    │         │           │
                        │              Distributor ◄───┘           │
                        │                    │                     │
                        │         Event Bus (mensageria)           │
                        │           ┌────────┤                     │
                        │    Node   │  Node  │  Node               │
                        │  (Chrome) │(Firefox)│ (Edge)             │
                        └─────────────────────────────────────────┘
```

Cada componente vira um **Deployment** independente com seu próprio **Service** interno.  
O **Router** é o único exposto externamente, via **Ingress** ou **LoadBalancer**.

---

## Pré-requisitos

- Cluster Kubernetes >= 1.25 (EKS, GKE, AKS, OpenShift, Rancher etc.)
- `kubectl` configurado e apontando para o cluster
- Helm 3 (opcional, mas recomendado para produção)
- Acesso ao registry de imagens da empresa (ou permissão para usar Docker Hub)
- Métricas de servidor habilitadas (`metrics-server`) para uso do HPA

Verifique:
```bash
kubectl version
kubectl top nodes   # deve funcionar se metrics-server estiver ativo
```

---

## Estrutura dos manifests

Sugestão de organização dentro do repositório:

```
k8s/
├── namespace.yaml
├── configmap.yaml
├── event-bus/
│   ├── deployment.yaml
│   └── service.yaml
├── sessions/
│   ├── deployment.yaml
│   └── service.yaml
├── session-queue/
│   ├── deployment.yaml
│   └── service.yaml
├── distributor/
│   ├── deployment.yaml
│   └── service.yaml
├── router/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yaml
├── node-chrome/
│   ├── deployment.yaml
│   ├── service.yaml
│   └── hpa.yaml
├── node-firefox/
│   ├── deployment.yaml
│   └── service.yaml
└── node-edge/
    ├── deployment.yaml
    └── service.yaml
```

---

## Passo a passo de implantação

### 1. Namespace e ConfigMap

**`k8s/namespace.yaml`**
```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: selenium-grid
  labels:
    app: selenium-grid
    env: production
```

**`k8s/configmap.yaml`** — centraliza todas as variáveis de ambiente dos componentes
```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: selenium-grid-config
  namespace: selenium-grid
data:
  SE_EVENT_BUS_HOST: "selenium-event-bus"
  SE_EVENT_BUS_PUBLISH_PORT: "4442"
  SE_EVENT_BUS_SUBSCRIBE_PORT: "4443"
  SE_SESSIONS_MAP_HOST: "selenium-sessions"
  SE_SESSIONS_MAP_PORT: "5556"
  SE_SESSION_QUEUE_HOST: "selenium-session-queue"
  SE_SESSION_QUEUE_PORT: "5559"
  SE_DISTRIBUTOR_HOST: "selenium-distributor"
  SE_DISTRIBUTOR_PORT: "5553"
  SE_NODE_MAX_SESSIONS: "5"
  SE_NODE_OVERRIDE_MAX_SESSIONS: "true"
```

Aplicar:
```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/configmap.yaml
```

---

### 2. Event Bus

O Event Bus é o **primeiro** componente a subir — todos os outros dependem dele.

**`k8s/event-bus/deployment.yaml`**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: selenium-event-bus
  namespace: selenium-grid
spec:
  replicas: 1
  selector:
    matchLabels:
      app: selenium-event-bus
  template:
    metadata:
      labels:
        app: selenium-event-bus
    spec:
      containers:
        - name: event-bus
          image: selenium/event-bus:4.18.1
          ports:
            - containerPort: 4442
            - containerPort: 4443
            - containerPort: 5557
          envFrom:
            - configMapRef:
                name: selenium-grid-config
          resources:
            requests:
              memory: "256Mi"
              cpu: "250m"
            limits:
              memory: "512Mi"
              cpu: "500m"
          readinessProbe:
            httpGet:
              path: /status
              port: 5557
            initialDelaySeconds: 10
            periodSeconds: 10
          livenessProbe:
            httpGet:
              path: /status
              port: 5557
            initialDelaySeconds: 20
            periodSeconds: 15
```

**`k8s/event-bus/service.yaml`**
```yaml
apiVersion: v1
kind: Service
metadata:
  name: selenium-event-bus
  namespace: selenium-grid
spec:
  selector:
    app: selenium-event-bus
  ports:
    - name: publish
      port: 4442
      targetPort: 4442
    - name: subscribe
      port: 4443
      targetPort: 4443
    - name: status
      port: 5557
      targetPort: 5557
```

---

### 3. Session Map, Session Queue e Distributor

Estes três componentes seguem o mesmo padrão. Exemplo para o **Distributor**:

**`k8s/distributor/deployment.yaml`**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: selenium-distributor
  namespace: selenium-grid
spec:
  replicas: 1
  selector:
    matchLabels:
      app: selenium-distributor
  template:
    metadata:
      labels:
        app: selenium-distributor
    spec:
      containers:
        - name: distributor
          image: selenium/distributor:4.18.1
          ports:
            - containerPort: 5553
          envFrom:
            - configMapRef:
                name: selenium-grid-config
          resources:
            requests:
              memory: "256Mi"
              cpu: "250m"
            limits:
              memory: "512Mi"
              cpu: "500m"
          readinessProbe:
            httpGet:
              path: /status
              port: 5553
            initialDelaySeconds: 15
            periodSeconds: 10
```

> Repita o mesmo padrão para `selenium/sessions:4.18.1` (porta 5556) e
> `selenium/session-queue:4.18.1` (porta 5559), ajustando os nomes e portas.

---

### 4. Router (ponto de entrada)

O **Router** é o único componente que recebe tráfego externo.

**`k8s/router/deployment.yaml`**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: selenium-router
  namespace: selenium-grid
spec:
  replicas: 1   # pode ser 2+ para alta disponibilidade
  selector:
    matchLabels:
      app: selenium-router
  template:
    metadata:
      labels:
        app: selenium-router
    spec:
      containers:
        - name: router
          image: selenium/router:4.18.1
          ports:
            - containerPort: 4444
          envFrom:
            - configMapRef:
                name: selenium-grid-config
          resources:
            requests:
              memory: "256Mi"
              cpu: "250m"
            limits:
              memory: "512Mi"
              cpu: "500m"
          readinessProbe:
            httpGet:
              path: /status
              port: 4444
            initialDelaySeconds: 20
            periodSeconds: 10
          livenessProbe:
            httpGet:
              path: /status
              port: 4444
            initialDelaySeconds: 30
            periodSeconds: 15
```

**`k8s/router/service.yaml`**
```yaml
apiVersion: v1
kind: Service
metadata:
  name: selenium-router
  namespace: selenium-grid
spec:
  selector:
    app: selenium-router
  ports:
    - port: 4444
      targetPort: 4444
  type: ClusterIP   # mude para LoadBalancer se não usar Ingress
```

---

### 5. Nodes de browser

Nodes precisam de `shm` extra (memória compartilhada) para rodar browsers sem crashar.

**`k8s/node-chrome/deployment.yaml`**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: selenium-node-chrome
  namespace: selenium-grid
spec:
  replicas: 2
  selector:
    matchLabels:
      app: selenium-node-chrome
  template:
    metadata:
      labels:
        app: selenium-node-chrome
    spec:
      volumes:
        - name: dshm
          emptyDir:
            medium: Memory
            sizeLimit: 2Gi
      containers:
        - name: node-chrome
          image: selenium/node-chrome:4.18.1
          ports:
            - containerPort: 5555
          envFrom:
            - configMapRef:
                name: selenium-grid-config
          env:
            - name: SE_NODE_MAX_SESSIONS
              value: "5"
            - name: SE_NODE_OVERRIDE_MAX_SESSIONS
              value: "true"
          volumeMounts:
            - mountPath: /dev/shm
              name: dshm
          resources:
            requests:
              memory: "1Gi"
              cpu: "500m"
            limits:
              memory: "2Gi"
              cpu: "1500m"
          readinessProbe:
            httpGet:
              path: /status
              port: 5555
            initialDelaySeconds: 15
            periodSeconds: 10
```

> Repita para `selenium/node-firefox:4.18.1` e `selenium/node-edge:4.18.1`.

---

### 6. Autoscaling com HPA

O **Horizontal Pod Autoscaler** aumenta e diminui os nodes de browser de acordo com a demanda.

**`k8s/node-chrome/hpa.yaml`**
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: selenium-node-chrome-hpa
  namespace: selenium-grid
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: selenium-node-chrome
  minReplicas: 1
  maxReplicas: 10
  metrics:
    - type: Resource
      resource:
        name: cpu
        target:
          type: Utilization
          averageUtilization: 70
```

> Para ambientes com muita demanda paralela, considere escalar baseado em métricas customizadas
> do Grid (sessões ativas via GraphQL) usando o [KEDA](https://keda.sh/) com o scaler do Selenium Grid.

---

### 7. Ingress corporativo

**`k8s/router/ingress.yaml`** — exemplo com NGINX Ingress Controller
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: selenium-grid-ingress
  namespace: selenium-grid
  annotations:
    nginx.ingress.kubernetes.io/proxy-read-timeout: "3600"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "3600"
    nginx.ingress.kubernetes.io/proxy-body-size: "512m"
spec:
  ingressClassName: nginx
  rules:
    - host: selenium-grid.sua-empresa.com
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: selenium-router
                port:
                  number: 4444
  tls:
    - hosts:
        - selenium-grid.sua-empresa.com
      secretName: selenium-grid-tls   # Secret com o certificado corporativo
```

Aplicar tudo:
```bash
kubectl apply -f k8s/event-bus/
kubectl apply -f k8s/sessions/
kubectl apply -f k8s/session-queue/
kubectl apply -f k8s/distributor/
kubectl apply -f k8s/router/
kubectl apply -f k8s/node-chrome/
kubectl apply -f k8s/node-firefox/
kubectl apply -f k8s/node-edge/

# Verificar status
kubectl get pods -n selenium-grid
kubectl get svc -n selenium-grid
```

Acessar o dashboard do Grid:
```
http://selenium-grid.sua-empresa.com/ui
```

---

## Executando os testes Maven no cluster

Para rodar os testes dentro do cluster (equivalente ao container `maven` do Docker Compose), use um **Job** Kubernetes:

```yaml
apiVersion: batch/v1
kind: Job
metadata:
  name: selenium-tests
  namespace: selenium-grid
spec:
  backoffLimit: 1
  template:
    spec:
      restartPolicy: Never
      containers:
        - name: maven
          image: maven:3.9.6-eclipse-temurin-11
          env:
            - name: REMOTE_URL
              value: "http://selenium-router:4444"
            - name: REMOTE_EXECUTION
              value: "true"
            - name: APP_URL_REMOTE
              value: "http://sua-app:3000"
          command:
            - bash
            - -c
            - |
              cd /selenium-agregator
              mvn test -Pfunctional-tests -DskipTests
              mvn test -Pfunctional-tests -Dtest=CrossBrowserTest -DfailIfNoTests=false
          volumeMounts:
            - name: source-code
              mountPath: /selenium-agregator
      volumes:
        - name: source-code
          # Em CI/CD, use um initContainer para clonar o repositório,
          # ou construa uma imagem Docker com o código embutido.
          emptyDir: {}
```

**Dica para CI/CD:** No pipeline (GitHub Actions, Jenkins, GitLab CI), substitua o Job por um `kubectl run` passando a imagem construída com o código dos testes, ou use `helm upgrade --install` com um chart customizado.

---

## Boas práticas corporativas

**Isolamento por ambiente**
Use um namespace por ambiente: `selenium-grid-dev`, `selenium-grid-staging`, `selenium-grid-prod`. Cada um com seu próprio ConfigMap e limites de recursos.

**Registry privado**
Se a empresa usa um registry interno, substitua as imagens base e configure `imagePullSecrets`:
```bash
kubectl create secret docker-registry corp-registry \
  --docker-server=registry.sua-empresa.com \
  --docker-username=usuario \
  --docker-password=senha \
  -n selenium-grid
```

**RBAC mínimo**
Crie uma `ServiceAccount` dedicada para o Selenium Grid com apenas as permissões necessárias, sem usar o `default` ServiceAccount.

**Recursos sempre definidos**
Nunca suba nodes de browser sem `resources.requests` e `resources.limits`. Browsers consomem muita memória e podem derrubar outros pods do cluster.

**Persistência do cache Maven**
Use um `PersistentVolumeClaim` para o diretório `.m2` do container Maven, evitando baixar dependências a cada execução:
```yaml
volumes:
  - name: m2-cache
    persistentVolumeClaim:
      claimName: maven-m2-cache
```

**Observabilidade**
O Selenium Grid 4 expõe métricas no formato OpenTelemetry. Configure as variáveis abaixo para enviar traces ao Jaeger ou Zipkin corporativo:
```yaml
env:
  - name: SE_ENABLE_TRACING
    value: "true"
  - name: SE_OTEL_TRACES_EXPORTER
    value: "jaeger"
  - name: SE_OTEL_EXPORTER_JAEGER_ENDPOINT
    value: "http://jaeger-collector:14250"
```

**Opção Helm (recomendada para produção)**
O projeto [docker-selenium](https://github.com/SeleniumHQ/docker-selenium) mantém um chart Helm oficial:
```bash
helm repo add selenium https://www.selenium.dev/docker-selenium-chart
helm repo update
helm install selenium-grid selenium/selenium-grid \
  --namespace selenium-grid \
  --create-namespace \
  --set global.seleniumGrid.imageTag=4.18.1 \
  --set chromeNode.replicas=3 \
  --set firefoxNode.replicas=2
```

---

## Troubleshooting

**Nodes não registram no Distributor**
Verifique se o `SE_EVENT_BUS_HOST` no ConfigMap resolve para o Service do Event Bus:
```bash
kubectl exec -it deploy/selenium-node-chrome -n selenium-grid -- \
  curl -s http://selenium-event-bus:5557/status
```

**Sessões ficam na fila mas não são alocadas**
```bash
# Verificar estado do Grid via GraphQL
curl -X POST http://selenium-grid.sua-empresa.com/graphql \
  -H 'Content-Type: application/json' \
  -d '{"query": "{ grid { totalSlots, usedSlots, sessionQueueSize } }"}'
```

**Crash de browser com OOMKilled**
Aumente o `sizeLimit` do volume `dshm` e o `limits.memory` do container do node.

**Pods reiniciando constantemente**
```bash
kubectl describe pod <nome-do-pod> -n selenium-grid
kubectl logs <nome-do-pod> -n selenium-grid --previous
```

---

## Referências

1. [Selenium Grid — Documentação Oficial](https://www.selenium.dev/documentation/grid/)
2. [Selenium Grid — Componentes](https://www.selenium.dev/documentation/grid/components/)
3. [Selenium Grid — Arquitetura](https://www.selenium.dev/documentation/grid/architecture/)
4. [docker-selenium — Helm Chart oficial](https://github.com/SeleniumHQ/docker-selenium)
5. [Kubernetes — HorizontalPodAutoscaler](https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/)
6. [KEDA — Kubernetes Event-driven Autoscaling](https://keda.sh/)
7. [OpenTelemetry com Selenium Grid](https://www.selenium.dev/documentation/grid/advanced_features/observability/)
