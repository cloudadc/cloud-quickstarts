# What's this 

This section for testing active health monitor.


## Deploy Ingress

kubectl_kpsitcls01 apply -f ingress.yaml

## Review NGINX Config

upstream f5-manage03-ingress-3-2-5-f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-5-f5manage03.test.cmbc.com-coffee-svc-80 512k;
        random two least_conn;                       
        server 197.21.4.132:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.4.137:8080 max_fails=1 fail_timeout=10s max_conns=0;
}  

location @hc-f5-manage03-ingress-3-2-5-f5manage03.test.cmbc.com-coffee-svc-80 {
        proxy_connect_timeout 4s;
        proxy_read_timeout 4s;
        proxy_send_timeout 4s;
        proxy_pass http://f5-manage03-ingress-3-2-5-f5manage03.test.cmbc.com-coffee-svc-80;
        health_check uri=/healthz/coffee interval=5s fails=3 passes=2;
}

## Test Access

 # curl -H "Host: f5manage03.test.cmbc.com" http://197.20.31.1:31617/coffee -I
HTTP/1.1 200 OK
Server: nginx/1.23.2
Date: Thu, 21 Sep 2023 03:22:33 GMT
Content-Type: text/plain
Content-Length: 433
Connection: keep-alive


## Use API to extract health check status

# curl http://197.20.31.1:32753/api/8/http/upstreams/f5-manage03-ingress-3-2-5-f5manage03.test.cmbc.com-coffee-svc-80
{"peers":[{"id":0,"server":"197.21.7.207:8080","name":"197.21.7.207:8080","backup":false,"weight":1,"state":"up","active":0,"requests":0,"responses":{"1xx":0,"2xx":0,"3xx":0,"4xx":0,"5xx":0,"codes":{},"total":0},"sent":0,"received":0,"fails":0,"unavail":0,"health_checks":{"checks":27,"fails":0,"unhealthy":0,"last_passed":true},"downtime":0}],"keepalive":0,"zombies":0,"zone":"f5-manage03-ingress-3-2-5-f5manage03.test.cmbc.com-coffee-svc-80"}

The following is extract from rest response, which show 27 times check and last check is success.

"health_checks":{"checks":27,"fails":0,"unhealthy":0,"last_passed":true},"downtime":0}

