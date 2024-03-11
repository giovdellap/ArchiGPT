var express = require('express');
var app = express();
var request = require("request")
var bodyParser = require('body-parser')
app.use(bodyParser.json());       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
}));
console.log("server avviato")


function sendToQueue(msg) {
  var amqp = require('amqplib/callback_api');
  amqp.connect('amqp://rabbitmq:5672', function (error0, connection) {
    if (error0) {
      throw error0;
    }
    connection.createChannel(function (error1, channel) {
      if (error1) {
        throw error1;
      }
      var queue = 'news';
      channel.assertQueue(queue, {
        durable: false
      });
      msg = JSON.stringify(msg)
      channel.sendToQueue(queue, Buffer.from(msg));
      console.log(" [x] Sent %s", msg);
    });
  });
}

app.post('/news', function (req, res) {
  articoli = req.body
  console.log(articoli)
  res.send("ok")
  //send article to the queue
  sendToQueue(articoli)
});
app.listen(3000);





