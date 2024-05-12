const express = require('express');
const request = require('request');
const amqp = require('amqplib/callback_api');
const calls=require("./api_calls.js");
const app = express();

function sendToQueue(msg, correlationId, replyTo, msgId) {
	amqp.connect('amqp://rabbitmq', function(err, conn) {
		conn.createChannel(function(err, ch) {
			var to_queue = replyTo;
			console.log(to_queue);
			ch.assertQueue(to_queue, { durable: false });
			ch.sendToQueue(to_queue, Buffer.from(msg),{ correlationId: correlationId, messageId: msgId });
			console.log('[x] Sent %s', msg);
		});
		setTimeout(function() { conn.close(); }, 500);
	});
}

//RABBITMQ config channel used to receive message spring->node(self)
amqp.connect('amqp://rabbitmq', function(err, conn) {
	conn.createChannel(function(err, ch) {
		var from_queue = 'spring_node';
		ch.assertQueue(from_queue, { durable: false });

		ch.consume(from_queue, function(msg) {

			var msgId = JSON.parse(msg.properties.messageId);
			var corrId = msg.properties.correlationId;
			var id = msgId.id;
			var replyTo = msg.properties.replyTo;

			switch(msgId.type){
				case "GRF":
					var symbol = msgId.ticker;
					calls.performGraph(symbol, corrId, replyTo, id);
					break;
				
				case "STCK":
					var symbol = msgId.ticker;
					calls.performStockData(symbol, corrId, replyTo, id);
					break;
					
				case "DATI":
					calls.performGetData(corrId, replyTo, id);
					break;
				
				case "NEWS":
					calls.performNews(corrId, replyTo, id);
					break;
				
				case "BOX":
					calls.performBoxes(corrId, replyTo, id);
					break;

				case "SEARCH":
					var search=msgId.q;
					calls.performSearchCall(search, corrId, replyTo, id);
					break;
				
				case "CRYPTO":
					calls.performCrypto(corrId, replyTo, id);
					break;

				default:
					sendToQueue("default", corrId, replyTo, id);
					break;
			}
		}, {noAck: true});
	});
});

module.exports.sendToQueue = sendToQueue;
