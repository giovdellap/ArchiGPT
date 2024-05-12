const express = require('express');
const enableWs = require('express-ws')

const amqp = require('./message_broker_functions.js')
const db = require('./db_functions.js')

// Constants
const SERVICE_NAME = process.env.SERVICE_NAME;
const PORT = process.env.PORT;
const WPORT = process.env.WPORT;
const HOST = '0.0.0.0';

const baseId = parseInt(SERVICE_NAME.charAt(SERVICE_NAME.length - 1))*100000;
var lastId = baseId;

var lastAnswer = ""
var res


// App
const app = express();
enableWs(app)

app.use(express.static('user_webapp'));


app.ws('/ws', (ws, req) => {

    var id

    ws.on('connection', msq =>{
        console.log("Connection Initialized");
        console.log(msg)
    })

    ws.on('message', msg => {

        msg = JSON.parse(msg)

        if (msg.ping) {
            setTimeout(function() {
                ws.send(JSON.stringify({'pong':true}));
            }, 20000)
        }
        else if (msg.hasOwnProperty("exist")){
            id=msg.exist
        }
        else if (msg.hasOwnProperty("nickname")){  //nickname set

            nick = msg.nickname
            console.log(lastId)
            lastId++;
            id = lastId;
            console.log(id)
            db.create(id, nick);

            response = {
                "id": id
            }

            ws.send(JSON.stringify(response))
        }

        else if(msg.hasOwnProperty("qn") && msg.hasOwnProperty("ans") && msg.hasOwnProperty("id")&& msg.hasOwnProperty("time")){

            if (msg.qn == questionNumber){
                id = msg.id
                score = 0
                time = msg.time
                if (msg.ans == lastAnswer){
                    score = 100 - time/100
                }
                else{
                    score = 0
                }
                db.update(id, score)
                db.update_questions(questionNumber,msg.ans)
            }
            
        }

        else{
            console.log(`wrong message received!`);

        }
    })

    ws.on('close', () => {
        console.log('WebSocket was closed')
    })


    amqp.eventemitter.on("start", function(data){
        msg = JSON.parse(data);
        lastAnswer = msg["correct"]
        questionNumber = msg["qn"]
        res = {'qn': questionNumber}
        ws.send(JSON.stringify(res))
    })

    amqp.eventemitter.once("end", async function(data){
        try {
            score = await db.get(id)
            console.log(score)
            res = {'end': score}
            ws.send(JSON.stringify(res))
            ws.close()
            setTimeout(function () {
                amqp.eventemitter.removeAllListeners("end");
                amqp.eventemitter.removeAllListeners("start")
            }, 2000)
        }
        catch{
            console.log("errore")
        }
    })


})





amqp.amqplisten();


app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
