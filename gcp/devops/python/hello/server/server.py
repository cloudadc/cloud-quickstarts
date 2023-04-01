try:
  from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
except ImportError:
  from http.server import BaseHTTPRequestHandler, HTTPServer

class SimpleHTTPRequestHandler(BaseHTTPRequestHandler):
  def do_GET(self):
    self.send_response(200)
    self.send_header('Content-type','text/plain')
    self.end_headers()
    self.wfile.write(b'Hello GCP dev!')
    return

  
def run():
  print('Server is starting...')
  server_address = ('0.0.0.0', 8080)
  server = HTTPServer(server_address, SimpleHTTPRequestHandler)
  print('Started. Press Ctrl + C to stop')
  server.serve_forever()
  
if __name__ == '__main__':
  run()
