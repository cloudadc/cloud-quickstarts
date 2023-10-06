#!/usr/bin/python3

from kubernetes import client, config, watch

kubeconfig_file = "/home/cloud_user_p_f5260f66/.kube/config"

config.load_kube_config(config_file=kubeconfig_file)
current_context = config.list_kube_config_contexts()[1]
cluster_name = current_context['context']['cluster']

api_instance = client.NetworkingV1Api()

crd_group = "apiextensions.k8s.io"
crd_version = "v1"
crd_plural = "VirtualServer"

api_instance_crd = client.CustomObjectsApi()

crd_list = api_instance_crd.list_cluster_custom_object(crd_group, crd_version, crd_plural)
for crd in crd_list['items']:
    print(f"CRD Name: {crd['metadata']['name']}")

w = watch.Watch()
for event in w.stream(api_instance.list_ingress_for_all_namespaces):
    ingress = event['object']
    print(f'Event Type: {event["type"]}, Ingress: {ingress.metadata.name}, Namespace: {ingress.metadata.namespace}')
    wideip = ingress.spec.rules[0].host
    status = ingress.status.load_balancer.ingress
    if status is not None and len(status) > 0 and status[0].ip is not None:     
        print("  " + wideip + " " + status[0].ip + " " + event["type"])
    else:
        print("  " + wideip + " " + event["type"])
