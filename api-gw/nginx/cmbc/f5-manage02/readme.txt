# What's this 

This sections contians nginx ingress controller installation for cookie persistence.

There are two controller be installed, each for ingress resource cookie persistence and gateway Virtual Server resource cookie persistence 
kubectl_kpsitcls01 apply -f ns-sa-cm.yaml
kubectl_kpsitcls01 apply -f nginx-plus-ingress.yaml
kubectl_kpsitcls01 apply -f nginx-plus-ingress-gw.yaml

Note that the above commands will create:

* ServiceAccount nginx-ingress which used by both nginx ingress controller
* Two Configmap which used by two nginx ingress controller coresponsingly
* Two ingress controller(including deploy, pod and service)

Refer to subfolder for detailed cookie persistence test. 
