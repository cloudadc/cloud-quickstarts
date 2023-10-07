# What's this 

The Cookie persistent which cookie will store in Broswer's Disk.

## Create Ingress

 kubectl_kpsitcls01 apply -f ingress.yaml

## Check the NGINX Configuration

upstream f5-manage02-cafe-ingress-f5manage02.test.cmbc.com-coffee-svc-80 {
        zone f5-manage02-cafe-ingress-f5manage02.test.cmbc.com-coffee-svc-80 512k;
        random two least_conn;
        server 197.21.2.142:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.61:8080 max_fails=1 fail_timeout=10s max_conns=0;
        sticky cookie srv_id expires=1h path=/coffee;
}

## TEST

# curl -H "Host: f5manage02.test.cmbc.com" http://197.20.31.1:31148/coffee -v
* Hostname was NOT found in DNS cache
*   Trying 197.20.31.1...
* Connected to 197.20.31.1 (197.20.31.1) port 31148 (#0)
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Date: Tue, 12 Sep 2023 06:45:00 GMT
< Content-Type: text/plain
< Content-Length: 429
< Connection: keep-alive
< Set-Cookie: srv_id=3a026c67ca5fc4c659689dc7baedb80f; expires=Tue, 12-Sep-23 07:45:00 GMT; max-age=3600; path=/coffee


curl --cookie "srv_id=3a026c67ca5fc4c659689dc7baedb80f; expires=Tue, 12-Sep-23 07:45:00 GMT; max-age=3600; path=/coffee" -H "Host: f5manage02.test.cmbc.com" http://197.20.31.1:31148/coffe

curl -s -H "Host: f5manage02.test.cmbc.com" http://$IPVS_EP/coffee -v            
> GET /coffee HTTP/1.1
> Host: f5manage02.test.cmbc.com
> User-Agent: curl/7.88.1
> Accept: */*
> 
< HTTP/1.1 200 OK
< Server: nginx/1.23.2
< Date: Sat, 07 Oct 2023 02:53:55 GMT
< Content-Type: text/plain
< Content-Length: 425
< Connection: keep-alive
< Set-Cookie: srv_id=57fc0e701258c85487fff7011a693f73; expires=Sat, 07-Oct-23 03:53:55 GMT; max-age=3600; path=/coffee
< 

            request: GET /coffee HTTP/1.1
                uri: /coffee
         request id: 5df4b067aba0bc316167111a35b92cd9
               host: f5manage02.test.cmbc.com
               date: 07/Oct/2023:02:53:55 +0000

        server name: coffee-6f9b66c984-rb9v4
        client addr: 197.21.7.77:39704
        server addr: 197.21.4.171:8080


$ echo "197.21.4.171:8080" | md5
57fc0e701258c85487fff7011a693f73



$ for i in {1..5}; do curl -s -H "Host: f5manage02.test.cmbc.com" --cookie "srv_id=57fc0e701258c85487fff7011a693f73; expires=Sat, 07-Oct-23 03:53:55 GMT; max-age=3600; path=/coffee" http://$IPVS_EP/coffee | grep addr ; echo ; done
        client addr: 197.21.7.77:54508
        server addr: 197.21.4.171:8080

        client addr: 197.21.7.77:54518
        server addr: 197.21.4.171:8080

        client addr: 197.21.4.130:55884
        server addr: 197.21.4.171:8080

        client addr: 197.21.7.77:54528
        server addr: 197.21.4.171:8080

        client addr: 197.21.4.130:55898
        server addr: 197.21.4.171:8080




## Test from F5

# curl -H "Host: f5manage02.test.cmbc.com" http://197.0.208.167:30657/coffee -v
* Hostname was NOT found in DNS cache
*   Trying 197.0.208.167...
* Connected to 197.0.208.167 (197.0.208.167) port 30657 (#0)
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Date: Tue, 12 Sep 2023 07:07:58 GMT
< Content-Type: text/plain
< Content-Length: 430
< Connection: keep-alive
< Set-Cookie: srv_id=1f85ad0b838e0a2b6386b092c24a79bf; expires=Tue, 12-Sep-23 08:07:58 GMT; max-age=3600; path=/coffee
<

            request: GET /coffee HTTP/1.1
                uri: /coffee
         request id: bf953a3925246bc1010b2c9b7679fc51
               host: f5manage02.test.cmbc.com
               date: 12/Sep/2023:07:07:58 +0000

        server name: coffee-54b5c6b9d-lwdxg
        client addr: 197.21.7.77:58426
        server addr: 197.21.2.142:8080


# for i in {1..5} ; do curl -s -H "Host: f5manage02.test.cmbc.com" --cookie "srv_id=1f85ad0b838e0a2b6386b092c24a79bf; expires=Tue, 12-Sep-23 08:07:58 GMT; max-age=3600; path=/coffee" http://197.0.208.167:30657/coffee | grep "addr" ; echo ; done
        client addr: 197.21.7.77:39180
        server addr: 197.21.2.142:8080

        client addr: 197.21.7.77:39182
        server addr: 197.21.2.142:8080

        client addr: 197.21.4.137:41980
        server addr: 197.21.2.142:8080

        client addr: 197.21.4.137:41982
        server addr: 197.21.2.142:8080

        client addr: 197.21.7.77:39188
        server addr: 197.21.2.142:8080

