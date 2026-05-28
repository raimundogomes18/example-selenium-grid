#!/bin/bash
cd /scripts

bash wait-grid.sh

bash wait-app.sh 

cd /selenium-agregator

mvn test -Pfunctional-tests -DskipTests

#mvn clean test -pl calculator-test
mvn test -Pfunctional-tests -Dtest=CrossBrowserTest -DfailIfNoTests=false
