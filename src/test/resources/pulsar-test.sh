#!/bin/bash

URL=pulsar+ssl://pc-a6ad1e00.aws-euc1-prod-snci-pool-slug.streamnative.aws.snio.cloud:6651
AUTH_PLUGIN=org.apache.pulsar.client.impl.auth.AuthenticationToken
TOKEN=eyJhbGciOiJSUzI1NiIsImtpZCI6ImY1N2M5MDlmLTk5OTMtNTA2OC04ODI5LTllODAwOTkxNDEwOSIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidXJuOnNuOnB1bHNhcjpvLTVwOWFyOnB1bHNhci1pbnN0YW5jZS0wMSJdLCJleHAiOjE3MjAyOTYwMjgsImh0dHBzOi8vc3RyZWFtbmF0aXZlLmlvL3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50QG8tNXA5YXIuYXV0aC5zdHJlYW1uYXRpdmUuY2xvdWQiLCJpYXQiOjE3MTc3MDQwMjksImlzcyI6Imh0dHBzOi8vcGMtYTZhZDFlMDAuYXdzLWV1YzEtcHJvZC1zbmNpLXBvb2wtc2x1Zy5zdHJlYW1uYXRpdmUuYXdzLnNuaW8uY2xvdWQvYXBpa2V5cy8iLCJqdGkiOiJhY2YwN2Q5NGMzMmQ0ZDcwOWMwZTBmMjRmMGE0NTBiNCIsInBlcm1pc3Npb25zIjpbImFkbWluIiwiYWNjZXNzIl0sInNjb3BlIjoiYWRtaW4gYWNjZXNzIiwic3ViIjoiVXJsM0VVT05NNWF5cDVYclBPY1FDV29JWnRLM1h0SW9AY2xpZW50cyJ9.aRk3lZjtC7UJHVu3iKwoC_Qud63NZYm2q9o29vsWjPUlldq2vk1Qu4gJz075D2HLL33UPOFZmzUVHwWYLNipexm5QXOswxjNIYHAzyB0hCMMUYVY3yhYKK9_ITc79PayJzIP1BuNUS045-24PvgWj9sY4X3s2jtuLB4mC7N04TpRsO2SFFIs1PcbBJuHjB584cb9fx07gJFkR4_L6xstJcvZuKk2yTOAVVhC0zTAXF0FyudaWMpcQgOkIIYzm9jwoxvSNMUpLScS4Fd32m3MyHAikM1YTsMqe3uhGbc9EF2GfeXO5kdc0xiZrwXN6OE40wxBNswHe-2FICM3txwadg
TOPIC_NAME=public/phishing-detection/sms-source

echo "Timestamp start: $(date -u +'%s%N')"
bin/pulsar-client --url $URL --auth-plugin $AUTH_PLUGIN --auth-params token:$TOKEN produce $TOPIC_NAME -m "{\"sender\":\"48797234595\",\"recipient\":\"6789\",\"message\":\"START\"}" -s "\n"
bin/pulsar-client --url $URL --auth-plugin $AUTH_PLUGIN --auth-params token:$TOKEN produce $TOPIC_NAME -m "{\"sender\":\"48797234596\",\"recipient\":\"6789\",\"message\":\"START\"}" -s "\n"

for (( i=1; i<=$1; i++ ))
do
  bin/pulsar-client --url $URL --auth-plugin $AUTH_PLUGIN --auth-params token:$TOKEN produce $TOPIC_NAME -n $2 -m "{\"sender\":\"48532771711\",\"recipient\":\"48797234595\",\"message\":\"hello, http://sciagnij-wirusa.pl\"}" -s "\n"
  bin/pulsar-client --url $URL --auth-plugin $AUTH_PLUGIN --auth-params token:$TOKEN produce $TOPIC_NAME -n $2 -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"Hello, google search: http://google.pl\"}" -s "\n"
  bin/pulsar-client --url $URL --auth-plugin $AUTH_PLUGIN --auth-params token:$TOKEN produce $TOPIC_NAME -n $2 -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234595\",\"message\":\"click pls, www.zwykla-strona.pl\"}" -s "\n"
  bin/pulsar-client --url $URL --auth-plugin $AUTH_PLUGIN --auth-params token:$TOKEN produce $TOPIC_NAME -n $2 -m "{\"sender\":\"48797234594\",\"recipient\":\"48797234596\",\"message\":\"click pls, https://www.m-bonk.pl.ng/personal-data\"}" -s "\n"
done
echo "Timestamp end: $(date -u +'%s%N')"