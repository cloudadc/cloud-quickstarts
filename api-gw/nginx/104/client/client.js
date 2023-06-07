// Author: Kylin SONG

const WebSocket = require('ws')

var urlArgs = process.argv.slice(2);
const url = urlArgs[0];
const msg = urlArgs[1];
console.log('Connect to Server via ', url);

const connection = new WebSocket(url)
 
connection.onopen = () => {
  connection.send(msg) 
}
 
connection.onerror = (error) => {
  console.log(`WebSocket error: ${error}`)
}
 
connection.onmessage = (e) => {
  console.log(e.data)
}
