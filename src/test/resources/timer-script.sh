#!/bin/bash

BROKER="172.31.44.7:9092,172.31.45.10:9092,172.31.37.181:9092"
PRODUCE_TOPIC="sms-source"

echo "Timestamp start: $(date -u +'%s%N')"

echo "{\"sender\":\"48797234595\",\"recipient\":\"6789\",\"message\":\"START\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC
echo "{\"sender\":\"48797234596\",\"recipient\":\"6789\",\"message\":\"START\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC

start_time=$(date +%s)

while [ $(($(date +%s) - start_time)) -lt 300 ]; do
  for (( i=1; i<=300; i++ )); do
    echo "{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}"
    \ | kcat -P -b $BROKER -t $PRODUCE_TOPIC
    echo "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"Hello, google search: http://google.pl\"}"
    \ | kcat -P -b $BROKER -t $PRODUCE_TOPIC
    echo "{\"sender\":\"48797234594\",\"recipient\":\"48797234595\",\"message\":\"click pls, www.zwykla-strona.pl\"}"
    \ | kcat -P -b $BROKER -t $PRODUCE_TOPIC
    echo "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"click pls, https://www.m-bonk.pl.ng/personal-data\"}"
    \ | kcat -P -b $BROKER -t $PRODUCE_TOPIC
  done
  sleep 15
done

echo "Timestamp end: $(date -u +'%s%N')"

