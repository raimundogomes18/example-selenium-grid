#!/bin/bash

url=$1

# Timeout configurável via variável de ambiente (padrão: 300 segundos)
WAIT_TIMEOUT_SECONDS=${WAIT_TIMEOUT_SECONDS:-300}
INTERVAL=10
MAX_RETRIES=$(( WAIT_TIMEOUT_SECONDS / INTERVAL ))

elapsed=0

echo "Aguardando serviço: $url (timeout: ${WAIT_TIMEOUT_SECONDS}s)"

for i in $(seq 1 $MAX_RETRIES); do
    code=$(curl -s -o /dev/null -w "%{http_code}" "$url")
    if [ "$code" == "200" ]; then
        echo -e "\n Serviço $url disponível após ${elapsed}s."
        exit 0
    fi

    sleep $INTERVAL
    elapsed=$(( elapsed + INTERVAL ))
    echo -n "...${elapsed}s"
done

echo -e "\n### [ERRO] Serviço $url não respondeu após ${WAIT_TIMEOUT_SECONDS}s."
exit 1
