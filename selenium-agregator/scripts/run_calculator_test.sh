#!/bin/bash
# wait-for-grid.sh

apt-get update

apt-get -y install jq 

cd ./selenium-agregator  

sh wait-grid.sh

sh wait-app.sh 

mvn clean test -pl calculator-test