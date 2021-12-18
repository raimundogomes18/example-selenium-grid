#!/bin/bash

elapsed_time=0

TIMEOUT_TRYS=$((10*1))

url=$1

for i in $(eval echo "{1..$TIMEOUT_TRYS}")
do
    code=$(curl -s -o /dev/null -w "%{http_code}" "$url")
    if [ "$code" == 200 ]; then
       echo  -e "\n Service $url started successfully!"
       exit 0
    else
        sleep 10

        let "elapsed_time+=10"

        echo -n "...$elapsed_time seg"
    fi    
done

echo -e "### [Erro] Service $url started with failure"
exit 1
