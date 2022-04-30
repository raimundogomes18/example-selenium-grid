##########################################################################################################################
# Guia de referência: https://docs.docker.com/engine/reference/builder/
# proxy: https://docs.docker.com/network/proxy/
###########################################################################################################################
FROM  jenkinsci/blueocean:1.25.3
USER root
COPY .gitconfig /root/.gitconfig
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

RUN apk add  py-pip python3-dev libffi-dev openssl-dev gcc libc-dev rust cargo make
RUN pip install --upgrade pip
RUN pip install docker-compose
RUN apk add maven
#USER jenkins
EXPOSE 8080



