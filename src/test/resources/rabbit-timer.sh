#!/bin/bash

EXCHANGE=amq.default
QUEUE_NAME=q.sms-source
PROPERTIES='{"content_type":"application/json"}'
PAYLOAD_ENCODING='string'

echo "Timestamp start: $(date -u +'%s%N')"
rabbitmqadmin publish exchange=$EXCHANGE routing_key=$QUEUE_NAME properties=$PROPERTIES
\ payload_encoding=$PAYLOAD_ENCODING payload="{\"sender\":\"48797234595\",\"recipient\":\"6789\",\"message\":\"START\"}"
rabbitmqadmin publish exchange=$EXCHANGE routing_key=$QUEUE_NAME properties=$PROPERTIES
\ payload_encoding=$PAYLOAD_ENCODING payload="{\"sender\":\"48797234596\",\"recipient\":\"6789\",\"message\":\"START\"}"

start_time=$(date +%s)

while [ $(($(date +%s) - start_time)) -lt 300 ]; do
  for (( i=1; i<=300; i++ )); do
    rabbitmqadmin publish exchange=$EXCHANGE routing_key=$QUEUE_NAME properties=$PROPERTIES
    \ payload_encoding=$PAYLOAD_ENCODING
    \payload="{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}"
    rabbitmqadmin publish exchange=$EXCHANGE routing_key=$QUEUE_NAME properties=$PROPERTIES
    \ payload_encoding=$PAYLOAD_ENCODING
    \ payload="{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"Hello, google search: http://google.pl\"}"
    rabbitmqadmin publish exchange=$EXCHANGE routing_key=$QUEUE_NAME properties=$PROPERTIES
    \ payload_encoding=$PAYLOAD_ENCODING
    \ payload="{\"sender\":\"48797234594\",\"recipient\":\"48797234595\",\"message\":\"click pls, www.zwykla-strona.pl\"}"
    rabbitmqadmin publish exchange=$EXCHANGE routing_key=$QUEUE_NAME properties=$PROPERTIES
    \ payload_encoding=$PAYLOAD_ENCODING
    \ payload="{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"click pls, https://www.m-bonk.pl.ng/personal-data\"}"
  done
  sleep 15
done

echo "Timestamp end: $(date -u +'%s%N')"