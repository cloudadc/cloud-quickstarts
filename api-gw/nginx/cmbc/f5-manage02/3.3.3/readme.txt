# What's this 

This test is for verifying NGINX keepalive behivior.

## Deploy

kubectl_kpsitcls01 apply -f vs.yaml

## COnfigration


upstream vs_f5-manage02_vs-3-3-3_coffee-app {
    zone vs_f5-manage02_vs-3-3-3_coffee-app 512k;
    random two least_conn;
    server 197.21.2.142:8080 max_fails=1 fail_timeout=10s max_conns=0;
    server 197.21.7.61:8080 max_fails=1 fail_timeout=10s max_conns=0;
    keepalive 30;
}

