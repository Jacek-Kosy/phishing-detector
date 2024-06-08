#!/bin/bash

URL=pulsar+ssl://pc-a6ad1e00.aws-euc1-prod-snci-pool-slug.streamnative.aws.snio.cloud:6651
TOPIC_NAME=public/phishing-detection/sms-source

echo "Timestamp start: $(date -u +'%s%N')"
bin/pulsar-client --url $URL produce $TOPIC_NAME
\ -m "{\"sender\":\"48797234595\",\"recipient\":\"6789\",\"message\":\"START\"}" -s "\n"
bin/pulsar-client --url $URL produce $TOPIC_NAME
\ -m "{\"sender\":\"48797234596\",\"recipient\":\"6789\",\"message\":\"START\"}" -s "\n"

start_time=$(date +%s)

while [ $(($(date +%s) - start_time)) -lt 300 ]; do
  for (( i=1; i<=$1; i++ )); do
    bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
    \ -m "{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}" -s "\n"
    bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
    \ -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"Hello, google search: http://google.pl\"}" -s "\n"
    bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
    \ -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234595\",\"message\":\"click pls, www.zwykla-strona.pl\"}" -s "\n"
    bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
    \ -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"click pls, https://www.m-bonk.pl.ng/personal-data\"}" -s "\n"
  done
  sleep 15
done
echo "Timestamp end: $(date -u +'%s%N')"

