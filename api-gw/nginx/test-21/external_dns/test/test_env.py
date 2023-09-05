import os

url = os.environ.get('BIGIP_URL')
username = os.environ.get('BIGIP_USERNAME')
password = os.environ.get('BIGIP_PASSWORD')

print("Connect to BIG-IP DNS https://" + url + " via " + username + "/****")
