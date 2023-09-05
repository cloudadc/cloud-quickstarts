import logging
import os
from kubernetes import client, config, watch

def get_logger():
    logger = logging.getLogger()
    logger.setLevel(logging.DEBUG)
    console_handler = logging.StreamHandler()
    console_handler.setLevel(logging.DEBUG)
    formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    console_handler.setFormatter(formatter)
    logger.addHandler(console_handler)
    return logger

def start():
    logger = get_logger()
    logger.info("DNS Automation Watcher Started")

    url = os.environ.get('BIGIP_URL')
    username = os.environ.get('BIGIP_USERNAME')
    password = os.environ.get('BIGIP_PASSWORD')
    logger.debug("Connect to BIG-IP DNS https://" + url + " via " + username + "/****")

    config.load_incluster_config()
    api_instance = client.NetworkingV1Api()
    w = watch.Watch()
    for event in w.stream(api_instance.list_ingress_for_all_namespaces):
        ingress = event['object']
        logger.debug(f'Event Type: {event["type"]}, Ingress: {ingress.metadata.name}, Namespace: {ingress.metadata.namespace}')
        wideip = ingress.spec.rules[0].host
        status = ingress.status.load_balancer.ingress
        if status is not None and len(status) > 0 and status[0].ip is not None:     
            logger.info("  " + wideip + " " + status[0].ip + " " + event["type"])
        else:
            logger.info("  " + wideip + " " + event["type"])

if __name__ == '__main__':
    start()