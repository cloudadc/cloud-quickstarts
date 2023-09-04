#!/usr/bin/python3

import yaml
import json
from kubernetes import client, config

def get_ingress_from_namespace(namespace):
    try:
        api_response = api_instance.list_namespaced_ingress(namespace=namespace)
        return api_response.items
    except Exception as e:
        print(f"Error: {str(e)}")

def write_to_file(file_path, data):
    try:
        json_string = json.dumps(data, indent=4)
        with open(file_path, "w") as file:
            file.write(json_string)
        print(f"{file_path}")
    except Exception as e:
        print(f"Error: {str(e)}")

kubeconfig_file = "/home/cloud_user_p_827ce902/.kube/config"
outputToConsole = False
namespaces = "test-01,test-02,test-20"

namespaces_raws = namespaces.split(",")
namespaces_list = [i.strip() for i in namespaces_raws if len(i.strip()) > 0]

config.load_kube_config(config_file=kubeconfig_file)
current_context = config.list_kube_config_contexts()[1]
cluster_name = current_context['context']['cluster']
api_instance = client.NetworkingV1Api()

for ns in namespaces_list:
    items = get_ingress_from_namespace(ns)
    for item in items:
        if outputToConsole:
            ingress_yaml = yaml.dump(item.to_dict(), default_flow_style=False)
            print(ingress_yaml)
        else:
            target_dict = item.to_dict()
            target_dict['metadata']['creation_timestamp'] = target_dict['metadata']['creation_timestamp'].isoformat()
            if target_dict['metadata']['managed_fields']:
                for f in target_dict['metadata']['managed_fields']:
                    f['time'] = f['time'].isoformat()
            file_path = cluster_name + "_" + ns + "_" + item.metadata.name + ".json"
            write_to_file(file_path, target_dict)