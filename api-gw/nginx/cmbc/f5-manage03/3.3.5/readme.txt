# What's this 

This section for testing active health monitor.


## Deploy VS

kubectl_kpsitcls01 apply -f vs.yaml

## Review NGINX Config


    location @hc-vs_f5-manage03_vs-3-3-5_tea-app {
        proxy_set_header Host "test.nginx.com";
        proxy_connect_timeout 10s;
        proxy_read_timeout 10s;
        proxy_send_timeout 10s;
        proxy_pass http://vs_f5-manage03_vs-3-3-5_tea-app;
        health_check uri=/health port=8080 interval=20s jitter=3s
            fails=5 passes=5 match=vs_f5-manage03_vs-3-3-5_tea-app_match
            ;
    }


## Test Access

# curl -H "Host: gw.f5manage03.test.cmbc.com" http://197.20.31.1:30304/tea -I
HTTP/1.1 200 OK
Server: nginx/1.23.2
Date: Thu, 21 Sep 2023 03:51:23 GMT
Content-Type: text/plain
Content-Length: 427
Connection: keep-alive


## Use API to extract health check status

# curl http://197.20.31.1:30670/api/8/http/upstreams/vs_f5-manage03_vs-3-3-5_tea-ap
{"peers":[{"id":0,"server":"197.21.7.201:8080","name":"197.21.7.201:8080","backup":false,"weight":1,"state":"up","active":0,"requests":2,"header_time":1,"response_time":1,"responses":{"1xx":0,"2xx":2,"3xx":0,"4xx":0,"5xx":0,"codes":{"200":2},"total":2},"sent":523,"received":714,"fails":0,"unavail":0,"health_checks":{"checks":22,"fails":0,"unhealthy":0,"last_passed":true},"downtime":0,"selected":"2023-09-21T03:51:23Z"}],"keepalive":0,"zombies":0,"zone":"vs_f5-manage03_vs-3-3-5_tea-app"}S

The following is extract from rest response, which show 22 times check and last check is success.

"health_checks":{"checks":22,"fails":0,"unhealthy":0,"last_passed":true},"downtime":0}

