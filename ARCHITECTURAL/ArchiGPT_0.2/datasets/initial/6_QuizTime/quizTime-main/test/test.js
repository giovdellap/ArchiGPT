const { countReset } = require('console');
const WebSocket = require('ws');
var fs = require('fs');

var names = fs.readFileSync('names.csv').toString().split(",");
var lett = ["A", "B", "C", "D"]
var count=0;

const NUM = 100;

function openws(){
    var nickname;
    var id;
    var qn;
    var res;
    var letter;
    var time;

    var socket = new WebSocket("ws://localhost:80/ws");

    socket.onopen = function(e) {
        nickname = names[count]
        count++;
        res = {'nickname':nickname}
        socket.send(JSON.stringify(res));
        res = {'ping':true}
        socket.send(JSON.stringify(res));
        console.log("opened " + count)
    };
    
    socket.onmessage = function(event) {
        msg = JSON.parse(event.data);
        if (msg.hasOwnProperty('pong')) {
            setTimeout(function() {
                res = {'ping':true}
                socket.send(JSON.stringify(res));
            }, 20000)
        }
        else if (msg.hasOwnProperty('id')) {
            id = msg.id;
            console.log("received id " + id)
        }
        else if (msg.hasOwnProperty('qn')) {
            qn = msg.qn;
            setTimeout(function(){
                letter = lett[Math.floor(Math.random() * 4)]
                time = Math.floor(Math.random() * 9999)+1
                res = {'qn': qn, 'ans': letter, 'id': id, 'time': time}
                socket.send(JSON.stringify(res));
                console.log(nickname+" answered "+letter)
            }, Math.floor(Math.random() * 8000))
        }
        else if (msg.hasOwnProperty('end')) {
            console.log(nickname+" scored "+msg.end);
        }
    };
    
    socket.onclose = function(event) {
        //console.log("ws closed");
    };
    
    socket.onerror = function(error) {
        console.log('error');
    };
}

for (i=0; i<NUM; i++){
    openws()
}