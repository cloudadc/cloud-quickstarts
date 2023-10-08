# What's this 

This section for testing active health monitor, with additional fine-grain and production-ready control.

* Active health moitor as mandatory
* Create a queue where incoming requests are temporatily store while NGINX Plus is checking the health of the endpoints after a configuration are reload.
* Slow start feature prevents a recently recovered server from being overwhelmed by connections. Avoid time-out, etc issures.

## Deploy Ingress

kubectl_kpsitcls01 apply -f ingress.yaml

## Review NGINX Config

upstream f5-manage03-cafe-3-2-6-enhance.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-cafe-3-2-6-enhance.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        least_conn;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0 slow_start=30s;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0 slow_start=30s;
        queue 500 timeout=4s;
}


## Test Access

# curl -H "Host: enhance.f5manage03.test.cmbc.com" http://197.20.31.1:31679/coffee  -I
HTTP/1.1 200 OK
Server: nginx/1.23.2
Date: Thu, 21 Sep 2023 06:37:56 GMT
Content-Type: text/plain
Content-Length: 440
Connection: keep-alive

## Use API to extract health check status

# curl http://197.20.31.1:32753/api/8/http/upstreams/f5-manage03-cafe-3-2-6-enhance.f5manage03.test.cmbc.com-coffee-svc-80
{"peers":[{"id":0,"server":"197.21.7.207:8080","name":"197.21.7.207:8080","backup":false,"weight":1,"state":"up","active":0,"requests":1,"header_time":1,"response_time":1,"responses":{"1xx":0,"2xx":1,"3xx":0,"4xx":0,"5xx":0,"codes":{"200":1},"total":1},"sent":274,"received":584,"fails":0,"unavail":0,"health_checks":{"checks":60,"fails":0,"unhealthy":0,"last_passed":true},"downtime":10,"selected":"2023-09-21T06:37:43Z"},{"id":1,"server":"197.21.7.47:8080","name":"197.21.7.47:8080","backup":false,"weight":1,"state":"up","active":0,"requests":1,"header_time":1,"response_time":1,"responses":{"1xx":0,"2xx":1,"3xx":0,"4xx":0,"5xx":0,"codes":{"200":1},"total":1},"sent":275,"received":144,"fails":0,"unavail":0,"health_checks":{"checks":60,"fails":0,"unhealthy":0,"last_passed":true},"downtime":10,"selected":"2023-09-21T06:37:56Z"}],"keepalive":0,"queue":{"size":0,"max_size":500,"overflows":0},"zombies":0,"zone":"f5-manage03-cafe-3-2-6-enhance.f5manage03.test.cmbc.com-coffee-svc-80"}

The following is extract from rest response, which show 60 times check and last check is success.

"health_checks":{"checks":60,"fails":0,"unhealthy":0,"last_passed":true}

