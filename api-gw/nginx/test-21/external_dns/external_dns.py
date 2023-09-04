#!/usr/bin/python3

from kubernetes import client, config, watch

print("DNS Automation Watcher Started")

config.load_incluster_config()
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
