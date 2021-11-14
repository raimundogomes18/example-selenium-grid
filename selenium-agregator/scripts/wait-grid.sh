#!/bin/bash

url=http://router:4444/status

echo $url

bash wait-service.sh $url