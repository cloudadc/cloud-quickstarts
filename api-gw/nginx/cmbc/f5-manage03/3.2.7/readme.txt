# What's this 

This is for persist test


## default(random two least_conn)

kubectl_kpsitcls01 apply -f ingress-default.yaml

### Review NGINX Config

upstream f5-manage03-ingress-3-2-7-default-monitor.default.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-default-monitor.default.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        random two least_conn;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## random + least_time

kubectl_kpsitcls01 apply -f ingress-random-least-time.yaml

### Review NGINX Config

upstream f5-manage03-ingress-3-2-7-monitor-random-least-time-monitor.randomf5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-monitor-random-least-time-monitor.randomf5manage03.test.cmbc.com-coffee-svc-80 512k;
        random two least_time=last_byte;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## least_time

kubectl_kpsitcls01 apply -f ingress-least-time.yaml

### Review NGINX Config

upstream f5-manage03-ingress-3-2-7-monitor-least-time-monitor.leasttime.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-monitor-least-time-monitor.leasttime.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        least_time last_byte;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## least_con

kubectl_kpsitcls01 apply -f ingress-least-conn.yaml

### Review NGINX Config

upstream f5-manage03-ingress-3-2-7-monitor-least-conn-monitor.leastconn.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-monitor-least-conn-monitor.leastconn.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        least_conn;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## round_robin

kubectl_kpsitcls01 apply -f ingress-round-robin.yaml

### Review NGINX Config

upstream f5-manage03-ingress-3-2-7-monitor-round-robin-monitor.roundrobin.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-monitor-round-robin-monitor.roundrobin.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}

## ip_hash

kubectl_kpsitcls01 apply -f ingress-ip-hash.yaml

### Review NGINX Config

upstream f5-manage03-ingress-3-2-7-monitor-ip-hash-monitor.iphash.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-monitor-ip-hash-monitor.iphash.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        ip_hash;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}

## hash

kubectl_kpsitcls01 apply -f ingress-hash.yaml

### Review NGINX Config


upstream f5-manage03-ingress-3-2-7-monitor-hash-monitor.hash.f5manage03.test.cmbc.com-coffee-svc-80 {
        zone f5-manage03-ingress-3-2-7-monitor-hash-monitor.hash.f5manage03.test.cmbc.com-coffee-svc-80 512k;
        hash $request_uri consistent;
        server 197.21.7.207:8080 max_fails=1 fail_timeout=10s max_conns=0;
        server 197.21.7.47:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


