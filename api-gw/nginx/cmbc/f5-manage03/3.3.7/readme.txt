# What's this 

This is for persist test


## default(random two least_conn)

kubectl_kpsitcls01 apply -f vs-default.yaml

### Review NGINX Config

upstream vs_f5-manage03_vs-3-3-7-default_foo {
    zone vs_f5-manage03_vs-3-3-7-default_foo 512k;
    random two least_conn;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## random + least_time

kubectl_kpsitcls01 apply -f vs-random-least-time.yaml

### Review NGINX Config

upstream vs_f5-manage03_vs-3-3-7-random-least-time_foo {
    zone vs_f5-manage03_vs-3-3-7-random-least-time_foo 512k;
    random two least_time=last_byte;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## least_time

kubectl_kpsitcls01 apply -f vs-least-time.yaml

### Review NGINX Config

upstream vs_f5-manage03_vs-3-3-7-least-time_foo {
    zone vs_f5-manage03_vs-3-3-7-least-time_foo 512k;
    least_time header;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## least_con

kubectl_kpsitcls01 apply -f vs-least-conn.yaml

### Review NGINX Config

tream vs_f5-manage03_vs-3-3-7-least-conn_foo {
    zone vs_f5-manage03_vs-3-3-7-least-conn_foo 512k;
    least_conn;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## round_robin

kubectl_kpsitcls01 apply -f vs-rr.yaml

### Review NGINX Config

upstream vs_f5-manage03_vs-3-3-7-rr_foo {
    zone vs_f5-manage03_vs-3-3-7-rr_foo 512k;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## ip_hash

kubectl_kpsitcls01 apply -f vs-ip-hash.yaml

### Review NGINX Config

upstream vs_f5-manage03_vs-3-3-7-ip-hash_foo {
    zone vs_f5-manage03_vs-3-3-7-ip-hash_foo 512k;
    ip_hash;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


## hash

kubectl_kpsitcls01 apply -f vs-hash.yaml

### Review NGINX Config

upstream vs_f5-manage03_vs-3-3-7-hash_foo {
    zone vs_f5-manage03_vs-3-3-7-hash_foo 512k;
    hash $request_uri consistent;
    server 197.21.7.201:8080 max_fails=1 fail_timeout=10s max_conns=0;
}


