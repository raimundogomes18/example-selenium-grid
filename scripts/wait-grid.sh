#!/bin/bash

url=$REMOTE_URL/status

bash wait-service.sh $url
