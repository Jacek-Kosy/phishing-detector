#!/bin/bash

URL=pulsar://ip-172-31-32-59:6650,ip-172-31-33-28:6650,ip-172-31-37-110:6650
TOPIC_NAME=public/phishing-detection/sms-source

echo "Timestamp start: $(date -u +'%s%N')"
bin/pulsar-client --url $URL  produce $TOPIC_NAME
\ -m "{\"sender\":\"48797234595\",\"recipient\":\"6789\",\"message\":\"START\"}" -s "\n"
bin/pulsar-client --url $URL  produce $TOPIC_NAME
\ -m "{\"sender\":\"48797234596\",\"recipient\":\"6789\",\"message\":\"START\"}" -s "\n"

for (( i=1; i<=$1; i++ ))
do
  bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
  \ -m "{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}" -s "\n"
  bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
  \ -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"Hello, google search: http://google.pl\"}" -s "\n"
  bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
  \ -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234595\",\"message\":\"click pls, www.zwykla-strona.pl\"}" -s "\n"
  bin/pulsar-client --url $URL produce $TOPIC_NAME -n $2
  \ -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"click pls, https://www.m-bonk.pl.ng/personal-data\"}" -s "\n"
done
echo "Timestamp end: $(date -u +'%s%N')"