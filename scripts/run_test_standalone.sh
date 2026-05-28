#!/bin/bash

cd /scripts 

bash wait-grid.sh

bash wait-app.sh 

cd /selenium-agregator  

mvn test -Pfunctional-tests -DskipTests

mvn test -Pfunctional-tests -Dtest=CalculatorBaseTest -DfailIfNoTests=false
