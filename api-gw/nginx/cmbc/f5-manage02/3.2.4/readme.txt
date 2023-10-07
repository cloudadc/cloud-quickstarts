# What's this 

This test is for verifying the cookie persist after apache keep-alive.

## Deploy

kubectl_kpsitcls01 apply -f httpd-reverse-proxy-config.yaml
kubectl_kpsitcls01 apply -f httpd.yaml

## tEST

 curl -H "Host: session.f5manage02.test.cmbc.com" http://197.20.31.2:32119/coffee -v
* Hostname was NOT found in DNS cache
*   Trying 197.20.31.2...
* Connected to 197.20.31.2 (197.20.31.2) port 32119 (#0)
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: session.f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
< Date: Tue, 12 Sep 2023 07:27:37 GMT
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Content-Type: text/plain
< Content-Length: 437
< Set-Cookie: nginx_coffee-svc-80=3a026c67ca5fc4c659689dc7baedb80f


# for i in {1..9} ; do curl -s --cookie "nginx_coffee-svc-80=3a026c67ca5fc4c659689dc7baedb80f" -H "Host: session.f5manage02.test.cmbc.com" http://197.20.31.2:32119/coffee | grep addr ; echo; done
        client addr: 197.21.4.137:59400
        server addr: 197.21.7.61:8080

        client addr: 197.21.4.137:59402
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.77:49178
        server addr: 197.21.7.61:8080

        client addr: 197.21.4.137:59404
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.77:49184
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.77:49190
        server addr: 197.21.7.61:8080

        client addr: 197.21.4.137:59406
        server addr: 197.21.7.61:8080

        client addr: 197.21.4.137:59408
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.77:49200
        server addr: 197.21.7.61:8080
