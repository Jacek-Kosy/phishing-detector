#!/bin/bash

BROKER="172.31.44.7:9092"
PRODUCE_TOPIC="sms-source"

echo "Timestamp start: $(date -u +'%s%N')"
echo "{\"sender\":\"48797234595\",\"recipient\":\"6789\",\"message\":\"START\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC
echo "{\"sender\":\"48797234596\",\"recipient\":\"6789\",\"message\":\"START\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC

for (( i=1; i<=2500; i++ ))
do
  echo "{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC
  echo "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"Hello, google search: http://google.pl\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC
  echo "{\"sender\":\"48797234594\",\"recipient\":\"48797234595\",\"message\":\"click pls, www.zwykla-strona.pl\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC
  echo "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"click pls, https://www.m-bonk.pl.ng/personal-data\"}" | kcat -P -b $BROKER -t $PRODUCE_TOPIC
done
echo "Timestamp end: $(date -u +'%s%N')"

