#!/bin/bash
# wait-for-grid.sh

cd ./selenium-agregator  

bash ./scripts/wait-grid.sh

bash ./scripts/wait-app.sh 

mvn install -DskipTests

#mvn clean test -pl calculator-test
mvn test -Dtest=CrossBrowserTest -DfailIfNoTests=false