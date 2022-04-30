export NODE_PORT=$(kubectl get -n default -o jsonpath="{.spec.ports[0].nodePort}" svc selenium-router)
export NODE_IP=$(kubectl get nodes -n default -o jsonpath="{.items[0].status.addresses[0].address}")

echo http://$NODE_IP:$NODE_PORT
export REMOTE_URL=http://$NODE_IP:$NODE_PORT
echo "Endereço SELENIUM: $REMOTE_URL"

export APP_PORT=$(kubectl get -n default -o jsonpath="{.spec.ports[0].nodePort}" svc app-calculadora)



export APP_URL_REMOTE=http://$NODE_IP:$APP_PORT
echo "Endereço APP: echo $APP_URL_REMOTE"

APP_URL_REMOTE=$APP_URL_REMOTE REMOTE_URL=$REMOTE_URL docker-compose up -d
