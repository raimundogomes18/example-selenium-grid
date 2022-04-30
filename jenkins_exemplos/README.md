# Projeto para auxiliar a execução dos testes funcionais automatizados(TFAs) 
 - Contém uma imagem do jenkins para ser executado como um container do docker
 - Jobs de template para auxiliar a execução dos TFAs via jenkins/docker.
 - Arquivo Vagranfile para configurar máquina virtual (VM) baseada no ubuntu com docker, docker-compose instalados.

## PRÉ-REQUISITOS:
 * docker instalado e configurado.
 * docker-compose instalado e configurado; No windows já vem com o docker. No linux é necessário [instalar](https://docs.docker.com/compose/install/)
  * A recomendação é iniciar o jenkins com docker-compose, mas caso não tenha ou não saiba configurar, mais abaixo tem explicando como proceder. 
 * Docker com o [proxy](https://docs.docker.com/network/proxy/) configurado, APENAS caso esteja configurando dentro da rede da Dataprev. 
  * Uma forma de saber se o docker está com o proxy configurado é imprimindo a variável de ambiente http_proxy de dentro de um container.
  ![proxy](imagens_para_documentacao/proxy.png)
  * Outra possibilidade é adicionar as variáveis http_proxy e https_proxy no arquivo ``jenkins_docker/.env``.

## INICIAR O JENKINS:
 - Faça o clone do repositório ``git clone https://www-scm.prevnet/raimundo.gneto/jenkins_exemplos`` 
 - Acesse a pasta jenkins_docker. ``cd jenkins_exemplos``  
 - Execute o comando:  `` docker-compose up -d`` para iniciar o jenkins.
 - Aguarde alguns segundos ou minutos, dependendo da configuração da máquina. Na primeira execução será criada a imagem do jenkins, então pode demorar mais de 30 minutos - Acesse o jenkins com a URL: ``http://locahost:8181/``.
 - Faça o login com usuário ``jenkins`` e senha ``jenkins``
 
## PARAR O JENKINS:
 - Execute o comando:  ``docker-compose stop`` para parar o serviço ou   ``docker-compose down`` para parar o jenkins e remover o container.      
  
## CONFIGURAÇÕES NO JENKINS:

## Changelog
