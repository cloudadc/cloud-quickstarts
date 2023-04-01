const http = require('http');
console.log('Server starting...');
http.createServer((req, res) => {
    console.log('Request received...');
    res.end('Hello dev!');
}).listen(8080, () => {
    console.log('started.');
});
