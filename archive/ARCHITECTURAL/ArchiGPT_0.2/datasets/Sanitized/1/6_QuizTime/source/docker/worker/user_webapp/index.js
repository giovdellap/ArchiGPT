var socket;
var nickname;
var id;
var qn;
var res;
var start;
var finish;

function init(){
    if (localStorage.hasOwnProperty('nickname')){
        document.getElementById("nick").value = localStorage['nickname']
    }
    if(localStorage.hasOwnProperty('time') && (Date.now() - parseInt(localStorage["time"]) >= 7200000)){
        localStorage.removeItem("id");
        localStorage["time"] = Date.now();
    }
    else if(!localStorage.hasOwnProperty("time")){
        localStorage["time"] = Date.now();
    }
}

function openws(){
    socket = new WebSocket("ws://localhost:80/ws");

    socket.onopen = function(e) {
        if (localStorage.hasOwnProperty('id') && localStorage["nickname"]==document.getElementById("nick").value){
            res = {'exist': localStorage['id'],
                    'nick': localStorage['nickname']}
            socket.send(JSON.stringify(res))
        }
        else {
            localStorage.removeItem("id");
            nickname = document.getElementById("nick").value;
            localStorage["nickname"] = nickname
            res = {'nickname':nickname}
            socket.send(JSON.stringify(res));
        }
        res = {'ping':true}
        socket.send(JSON.stringify(res));
        document.getElementById("score").hidden = true;
        document.getElementById("nickname").hidden = true;
        document.getElementById("buttons").hidden = true;
        document.getElementById("loading").hidden = false;
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
            localStorage["id"] = id;
    
        }
        else if (msg.hasOwnProperty('qn')) {
            qn = msg.qn;
            document.getElementById("loading").hidden = true;
            document.getElementById("score").hidden = true;
            document.getElementById("nickname").hidden = true;
            document.getElementById("buttons").hidden = false;

            start = Date.now();

            setTimeout(function() {
                document.getElementById("score").hidden = true;
                document.getElementById("nickname").hidden = true;
                document.getElementById("buttons").hidden = true;
                document.getElementById("loading").hidden = false;
            }, 10000)

        }
        else if (msg.hasOwnProperty('end')) {
            document.getElementById('rank').textContent = msg.end;
            document.getElementById('cname').textContent = localStorage['nickname'];
            document.getElementById("nickname").hidden = true;
            document.getElementById("buttons").hidden = true;
            document.getElementById("loading").hidden = true;
            document.getElementById("score").hidden = false;
        }
        else {
            console.log("Unknown message received");
            console.log(msg)
        }
    };
    
    socket.onclose = function(event) {
        if (event.wasClean) {
            localStorage.removeItem('id');
            //alert('Thanks for playing with us!');
        } else {
            document.getElementById("buttons").hidden = true;
            document.getElementById("loading").hidden = true;
            document.getElementById("score").hidden = true;
            document.getElementById("nickname").hidden = false;
             console.log('Unexpected error');
             console.log(event);
        }
    };
    
    socket.onerror = function(error) {
        document.getElementById("buttons").hidden = true;
        document.getElementById("loading").hidden = true;
        document.getElementById("score").hidden = true;
        document.getElementById("nickname").hidden = false;
        console.log('Unexpected error');
        console.log(error);
    };
}

function send(letter){
    finish = Date.now() - start
    res = {'qn': qn, 'ans': letter, 'id': localStorage['id'], 'time': finish}
    socket.send(JSON.stringify(res));
    document.getElementById("score").hidden = true;
    document.getElementById("nickname").hidden = true;
    document.getElementById("buttons").hidden = true;
    document.getElementById("loading").hidden = false;
}