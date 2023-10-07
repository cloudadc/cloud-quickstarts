# What's this 

The Cookie persistent which cookie will store in Broswer's Disk and gateway template be used

## Create Ingress

  kubectl_kpsitcls01 apply -f vs.yaml

## Check the NGINX Configuration

upstream vs_f5-manage02_vs-3-3-1_coffee-app {
    zone vs_f5-manage02_vs-3-3-1_coffee-app 512k;
    random two least_conn;
    server 197.21.2.142:8080 max_fails=1 fail_timeout=10s max_conns=0;
    server 197.21.7.61:8080 max_fails=1 fail_timeout=10s max_conns=0;
    sticky cookie srv_id expires=1h domain=.test.cmbc.com secure path=/coffee;
}


## TEST

# curl -H "Host: gw.f5manage02.test.cmbc.com" http://197.20.31.1:31203/coffee -v
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: gw.f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Date: Tue, 12 Sep 2023 09:14:35 GMT
< Content-Type: text/plain
< Content-Length: 433
< Connection: keep-alive
< Set-Cookie: srv_id=1f85ad0b838e0a2b6386b092c24a79bf; expires=Tue, 12-Sep-23 10:14:35 GMT; max-age=3600; domain=.test.cmbc.com; secure; path=/coffee
<

            request: GET /coffee HTTP/1.1
                uri: /coffee
         request id: f71734316f6204d55f6abeb5eb8140c3
               host: gw.f5manage02.test.cmbc.com
               date: 12/Sep/2023:09:14:35 +0000

        server name: coffee-54b5c6b9d-lwdxg
        client addr: 197.21.7.57:59936
        server addr: 197.21.2.142:8080

             cookie:
                xff:
         user agent: curl/7.37.0

* Connection #0 to host 197.20.31.1 left intact


curl --cookie "srv_id=1f85ad0b838e0a2b6386b092c24a79bf; expires=Tue, 12-Sep-23 10:14:35 GMT; max-age=3600; domain=.test.cmbc.com; secure; path=/coffee" -H "Host: gw.f5manage02.test.cmbc.com" http://197.20.31.1:31203/coffee

