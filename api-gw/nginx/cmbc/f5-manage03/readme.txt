# What's this 

This sections contains test case:

* 3.2.5
* 3.2.6
* 3.2.7
* 3.3.5
* 3.3.6
* 3.3.7

## Deploy NGINX

kubectl_kpsitcls01 apply -f ns-sa-cm.yaml
kubectl_kpsitcls01 apply -f nginx-plus-ingress.yaml
kubectl_kpsitcls01 apply -f nginx-plus-ingress-gw.yaml

Review the log and config

NS=f5-manage03 && IC_POD=$(kubectl_kpsitcls01 get pods -n $NS -l app=nginx-ingress --no-headers | head -n 1 | awk '{print $1}') && kubectl_kpsitcls01 logs -f $IC_POD -n $NS

NS=f5-manage03 && IC_POD=$(kubectl_kpsitcls01 get pods -n $NS -l app=nginx-ingress-gw --no-headers | head -n 1 | awk '{print $1}') && kubectl_kpsitcls01 logs -f $IC_POD -n $NS

NS=f5-manage03 && IC_POD=$(kubectl_kpsitcls01 get pods -n $NS -l app=nginx-ingress --no-headers | head -n 1 | awk '{print $1}') && kubectl_kpsitcls01 exec $IC_POD -n $NS -- nginx -T 2>&1 | grep -v '^[[:space:]]*$'

NS=f5-manage03 && IC_POD=$(kubectl_kpsitcls01 get pods -n $NS -l app=nginx-ingress-gw --no-headers | head -n 1 | awk '{print $1}') && kubectl_kpsitcls01 exec $IC_POD -n $NS -- nginx -T 2>&1 | grep -v '^[[:space:]]*$'


## Deploy App

kubectl_kpsitcls01 apply -f app-coffee.yaml
kubectl_kpsitcls01 apply -f app-tea.yaml

## Usage Guide

Refer to subfolder for details
