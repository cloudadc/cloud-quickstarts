#!/usr/bin/python3

from kubernetes import client, config, watch

kubeconfig_file = "/home/cloud_user_p_c8b1c4a9/.kube/config"

config.load_kube_config(config_file=kubeconfig_file)
current_context = config.list_kube_config_contexts()[1]
cluster_name = current_context['context']['cluster']

api_instance = client.NetworkingV1Api()
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
