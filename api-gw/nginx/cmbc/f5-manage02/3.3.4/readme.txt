# What's this 

This test is for verifying the cookie persist after apache keep-alive.

The Topoloy:

kube-proxy -> apache -> kube-proxy -> nginx -> pod

## Deploy

kubectl_kpsitcls01 apply -f httpd-reverse-proxy-config.yaml
kubectl_kpsitcls01 apply -f httpd.yaml

## tEST

# curl -H "Host: gw.session.f5manage02.test.cmbc.com" http://197.20.31.1:30399/coffee -v
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: gw.session.f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
< Date: Tue, 12 Sep 2023 09:45:31 GMT
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Content-Type: text/plain
< Content-Length: 441
< Set-Cookie: coffee_svc_cookie=1f85ad0b838e0a2b6386b092c24a79bf
<

            request: GET /coffee HTTP/1.1
                uri: /coffee
         request id: b6426691c058862e027988130416522b
               host: gw.session.f5manage02.test.cmbc.com
               date: 12/Sep/2023:09:45:31 +0000

        server name: coffee-54b5c6b9d-lwdxg
        client addr: 197.21.7.57:39144
        server addr: 197.21.2.142:8080

             cookie:
                xff:
         user agent: curl/7.37.0

* Connection #0 to host 197.20.31.1 left intact


# for i in {1..6}; do curl -s --cookie "coffee_svc_cookie=3a026c67ca5fc4c659689dc7baedb80f" -H "Host: gw.session.f5manage02.test.cmbc.com" http://197.20.31.1:30399/coffee | grep addr ; echo ; done
        client addr: 197.21.7.51:46254
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.57:42630
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.51:46268
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.57:42636
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.51:46272
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.57:42644
        server addr: 197.21.7.61:8080

