# What's this 

This section for testing active health monitor, with additional fine-grain and production-ready control.

* Active health moitor as mandatory
* Create a queue where incoming requests are temporatily store while NGINX Plus is checking the health of the endpoints after a configuration are reload.
* Slow start feature prevents a recently recovered server from being overwhelmed by connections. Avoid time-out, etc issures.
* define retuen status match validation
* customize interval, timeout, etc.
* health monitor persistent, which status are keeped before and after reload
* health monitor re-use connection
* health monitor do not depend on POD level probe

## Deploy VS

kubectl_kpsitcls01 apply -f ingress.yaml


## Review NGINX Config


upstream vs_f5-manage03_vs-3-3-6_tea-app {
    zone vs_f5-manage03_vs-3-3-6_tea-app 512k;
    random two least_conn;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
    queue 500 timeout=60s;
}
match vs_f5-manage03_vs-3-3-6_tea-app_match {
    status ! 500;
}
server {
    listen 80;
    listen [::]:80;
    server_name gw.enahance.f5manage03.test.cmbc.com;
    status_zone gw.enahance.f5manage03.test.cmbc.com;
    set $resource_type "virtualserver";
    set $resource_name "vs-3-3-6";
    set $resource_namespace "f5-manage03";
    server_tokens "on";
    location @hc-vs_f5-manage03_vs-3-3-6_tea-app {
        proxy_set_header Host "test.nginx.com";
        proxy_connect_timeout 10s;
        proxy_read_timeout 10s;
        proxy_send_timeout 10s;
        proxy_pass http://vs_f5-manage03_vs-3-3-6_tea-app;
        health_check uri=/health port=8080 interval=20s jitter=3s
            fails=5 passes=5 match=vs_f5-manage03_vs-3-3-6_tea-app_match
             mandatory persistent
            ;
    }



## Test Access

# curl -H "Host: gw.enahance.f5manage03.test.cmbc.com" http://197.20.31.1:30304/tea  -I
HTTP/1.1 200 OK
Server: nginx/1.23.2
Date: Thu, 21 Sep 2023 07:08:14 GMT
Content-Type: text/plain
Content-Length: 436
Connection: keep-alive

## Use API to extract health check status

# curl http://197.20.31.1:30670/api/8/http/upstreams/vs_f5-manage03_vs-3-3-6_tea-app
{"peers":[{"id":0,"server":"197.21.7.201:8080","name":"197.21.7.201:8080","backup":false,"weight":1,"state":"up","active":0,"requests":1,"header_time":1,"response_time":1,"responses":{"1xx":0,"2xx":1,"3xx":0,"4xx":0,"5xx":0,"codes":{"200":1},"total":1},"sent":280,"received":144,"fails":0,"unavail":0,"health_checks":{"checks":30,"fails":0,"unhealthy":0,"last_passed":true},"downtime":0,"selected":"2023-09-21T07:08:14Z"}],"keepalive":0,"queue":{"size":0,"max_size":500,"overflows":0},"zombies":0,"zone":"vs_f5-manage03_vs-3-3-6_tea-app"}

The following is extract from rest response, which show 30 times check and last check is success.

"health_checks":{"checks":30,"fails":0,"unhealthy":0,"last_passed":true}

