# What's this 

This is for testing seesion cookie in gw vs mode

## Create Ingress

 kubectl_kpsitcls01 apply -f vs.yaml

## Check the NGINX Configuration

upstream vs_f5-manage02_vs-3-3-2_coffee-app {
    zone vs_f5-manage02_vs-3-3-2_coffee-app 512k;
    random two least_conn;
    server 197.21.2.142:8080 max_fails=1 fail_timeout=10s max_conns=0;
    server 197.21.7.61:8080 max_fails=1 fail_timeout=10s max_conns=0;
    sticky cookie coffee_svc_cookie;
}


## TEST

# curl -H "Host: gw.session.f5manage02.test.cmbc.com" http://197.20.31.1:31203/coffee -v
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: gw.session.f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Date: Tue, 12 Sep 2023 09:26:27 GMT
< Content-Type: text/plain
< Content-Length: 440
< Connection: keep-alive
< Set-Cookie: coffee_svc_cookie=3a026c67ca5fc4c659689dc7baedb80f
<

            request: GET /coffee HTTP/1.1
                uri: /coffee
         request id: 83c76c6adf950778af482f879211f5e7
               host: gw.session.f5manage02.test.cmbc.com
               date: 12/Sep/2023:09:26:27 +0000

        server name: coffee-54b5c6b9d-cchhv
        client addr: 197.21.7.51:51992
        server addr: 197.21.7.61:8080

             cookie:
                xff:
         user agent: curl/7.37.0

* Connection #0 to host 197.20.31.1 left intact


# for i in {1..5} ; do curl -s --cookie "coffee_svc_cookie=3a026c67ca5fc4c659689dc7baedb80f" -H "Host: gw.session.f5manage02.test.cmbc.com" http://197.20.31.1:31203/coffee | grep addr ; echo ; done
        client addr: 197.21.7.51:56608
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.57:52982
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.51:56622
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.57:52992
        server addr: 197.21.7.61:8080

        client addr: 197.21.7.51:56630
        server addr: 197.21.7.61:8080

## Conclusion
