#!/bin/bash

cat <<EOL >> server.py
import http.server
import socketserver
import socket

PORT = 80

class MyRequestHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header("Content-type", "text/html")
        self.end_headers()
        response = f"""
        <html>
        <head><title>Server Info</title></head>
        <body>
        <h1>Server Information</h1>
        <p>APP run on $(hostname)</p>
        </body>
        </html>
        """
        self.wfile.write(response.encode('utf-8'))

Handler = MyRequestHandler

with socketserver.TCPServer(("", PORT), Handler) as httpd:
    httpd.serve_forever()
EOL

sudo nohup python3 server.py &
