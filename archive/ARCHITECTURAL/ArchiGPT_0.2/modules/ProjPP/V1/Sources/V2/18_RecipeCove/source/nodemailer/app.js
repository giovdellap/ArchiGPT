//subscriber
'use strict';

const amqplib = require('amqplib/callback_api');
const nodemailer = require('nodemailer');

console.log('Starting nodemailer...');

// Create a transporter object that will be used to send emails (with Gmail and nodemailer)
const transporter = nodemailer.createTransport({
	// Tutorial from https://miracleio.me/snippets/use-gmail-with-nodemailer
	// Email: noreply.recipecove@gmail.com
	// Password: PaSSWoRD
	// App password: zvmz okvx xegh wfou
	// NOTE: the "App password" is the password to use in the code below
	service: 'gmail',
	auth: {
		user: 'noreply.recipecove@gmail.com',
		pass: 'zvmz okvx xegh wfou'
	}
});

transporter.verify((error, success) => {
	if (error) {
		console.error('Error verifying transporter...');
		console.error(error);
	}
	else {
		console.log('Ready to send mail!');
	}
})

// connect to rabbitmq server
amqplib.connect('amqp://guest:guest@rabbitmq', (err, connection) => {
	if (err) {
		console.error(err.stack);
		return process.exit(1);
	}
	// creeates a channel to communicate with rabbitmq
	connection.createChannel((err, channel) => {
		if (err) {
			console.error(err.stack);
			return process.exit(1);
		}

		var queue = 'registration_email_queue';

		// Make sure the queue exists
		channel.assertQueue(queue, {
			durable: true
		}, err => {
			if (err) {
				console.error(err.stack);
				return process.exit(1);
			}

			// Only fetch one message at a time
			channel.prefetch(1);

			// Consume messages from the queue (i.e. do something with the messages, only one at a time in this case)
			channel.consume(queue, data => {
				if (data === null) {
					return;
				}

				// Get the message from the queue (and parse it as JSON)
				let message = JSON.parse(data.content.toString());

				// Get email and username from the JSON message
				let email = message.email;
				let username = message.username;

				console.log('Sending email to ' + email + '(' + username + ')');

				// Specify the email options
				var mailOptions = {
					from: 'noreply.recipecove@gmail.com',
					to: email,
					subject: 'Welcome to Recipe Cove',
					text:
						'Hi ' + username + ',\n\n' +
						'Thank you for signing up for Recipe Cove!\n\n' +
						'Enjoy,\n' +
						'- The Recipe Cove Team'
				}

				// Send the email using nodemailer
				transporter.sendMail(mailOptions, (err, info) => {
					if (err) {
						console.error(err.stack);
						return channel.nack(data);
					}
					channel.ack(data);
				});
			}, { noAck: false });
		});
	});
});

