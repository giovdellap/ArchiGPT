const express = require('express');
const app = express();

var amqp = require('amqplib/callback_api');

var cors = require('cors')
app.use(cors())

app.use(express.json());

const { Client } = require('@elastic/elasticsearch')
const client = new Client({ node: 'http://elasticsearch:9200' })

// Constants
const PORT = process.env.PORT;
const HOST = '0.0.0.0';
const URLRABBIT = "amqp://rabbitmq"

// App

app.post('/start', (req, res) => {
    console.log('Question starting');

    qn = req.body.qn

    client.create({
        index: 'questions',
        id: qn,
        body: {
          A: 0,
          B: 0,
          C: 0,
          D: 0
        }
    })

    amqp.connect(URLRABBIT, function(error0, connection) {
        if (error0) {
            throw error0;
        }
        connection.createChannel(function(error1, channel) {
            if (error1) {
                throw error1;
            }
            var exchange = 'starts';
            var msg = JSON.stringify(req.body);
    
            channel.assertExchange(exchange, 'fanout', {
                durable: false
            });
            channel.publish(exchange, '', Buffer.from(msg));
            console.log("Sent %s", msg);
        });
        setTimeout(function() {
            connection.close();
        }, 500);
    });

    res.send({'msg':'Question started'});
});


app.post('/end', (req, res) => {
    console.log('Ending game...');

    amqp.connect(URLRABBIT, function(error0, connection) {
        if (error0) {
            throw error0;
        }
        connection.createChannel(function(error1, channel) {
            if (error1) {
                throw error1;
            }
            var exchange = 'ends';
            var msg = JSON.stringify(req.body);
    
            channel.assertExchange(exchange, 'fanout', {
                durable: false
            });
            channel.publish(exchange, '', Buffer.from(msg));
            console.log("Sent %s", msg);
        });
        setTimeout(function() {
            connection.close();
        }, 500);
    });

    setTimeout(function(){
        client.indices.delete({ index: 'questions' })
        client.indices.delete({ index: 'game' })


        setTimeout(function(){
            client.indices.create({ index: 'questions' })
            client.indices.create({ index: 'game' })
            setTimeout(function(){
                client.create({
                    index: 'game',
                    id: '0',
                    body: {
                    nickname: 'admin',
                    score: 0
                    }
                })
                client.create({
                    index: 'questions',
                    id: '0',
                    body: {
                    A: 0,
                    B: 0,
                    C: 0,
                    D: 0
                    }
                })
            }, 1000)
        }, 1000)

        res.send({'msg':'Game ended'});
        
    }, 10000)
    
});


app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
