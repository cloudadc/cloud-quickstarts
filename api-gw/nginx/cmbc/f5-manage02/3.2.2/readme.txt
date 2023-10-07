# What's this 

The Cookie persistent which cookie will store in Broswer's Disk.

## Create Ingress

 kubectl_kpsitcls01 apply -f ingress.yaml

## Check the NGINX Configuration

tream f5-manage02-test-session-cookie-ingress-session.f5manage02.test.cmbc.com-coffee-svc-80 {
        zone f5-manage02-test-session-cookie-ingress-session.f5manage02.test.cmbc.com-coffee-svc-80 512k;
        random two least_conn;
        server 197.21.2.142:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.61:8080 max_fails=1 fail_timeout=10s max_conns=0;
        sticky cookie nginx_coffee-svc-80;
}


## TEST

# curl -H "Host: session.f5manage02.test.cmbc.com" http://197.20.31.1:31148/coffee  -v
* Hostname was NOT found in DNS cache
*   Trying 197.20.31.1...
* Connected to 197.20.31.1 (197.20.31.1) port 31148 (#0)
> GET /coffee HTTP/1.1
> User-Agent: curl/7.37.0
> Accept: */*
> Host: session.f5manage02.test.cmbc.com
>
< HTTP/1.1 200 OK
* Server nginx/1.23.2 is not blacklisted
< Server: nginx/1.23.2
< Date: Tue, 12 Sep 2023 07:00:53 GMT
< Content-Type: text/plain
< Content-Length: 437
< Connection: keep-alive
< Set-Cookie: nginx_coffee-svc-80=3a026c67ca5fc4c659689dc7baedb80f


curl --cookie "nginx_coffee-svc-80=1f85ad0b838e0a2b6386b092c24a79bf" -H "Host: session.f5manage02.test.cmbc.com" http://197.20.31.1:31148/coffee

## Conclusion

cookie are always kept in broswer, if cookie without any attributes then it persisted in memory, which means once the broswer closed, the cookie will expire.
