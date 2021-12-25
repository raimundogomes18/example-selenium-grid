#!/bin/bash

cd /scripts 

bash wait-grid.sh

bash wait-app.sh 

cd /selenium-agregator  

mvn package -DskipTests

mvn test -Dtest=CalculatorBaseTest -DfailIfNoTests=false