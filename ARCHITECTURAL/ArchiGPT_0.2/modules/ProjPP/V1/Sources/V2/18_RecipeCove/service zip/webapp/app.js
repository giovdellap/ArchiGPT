var express = require('express');
var app = express();
var fs = require('fs');
var bodyParser = require("body-parser");
var expressSession = require('express-session');
app.use(bodyParser.urlencoded({ extended: false }));
var request = require('request');
const WebSocket = require('ws');
const amqplib = require('amqplib/callback_api');
const https = require('https')
const { info } = require('console');
var { connected } = require('process');
require('dotenv').config();

const port = 3000;

app.set('view engine', 'ejs');
app.set('views', __dirname + '/views');
app.use(express.static(__dirname + '/views'));

// management of the session
app.use(expressSession({
	secret: 'RecipeCove',
	resave: false,
	saveUninitialized: false,
	cookie: { maxAge: 24 * 60 * 60 * 1000, secure: true } // duration of 24 hours + security
	// default: { path: '/', httpOnly: true, secure: false, maxAge: null }
}));

app.use(function (req, res, next) {
	res.locals.session = req.session;
	next();
});


/* ********************************* HTTPS SERVER ****************************************** */

const server = https.createServer({
	key: fs.readFileSync('security/key.pem'),
	cert: fs.readFileSync('security/cert.pem')
}, app);

server.addListener('upgrade', (req, res, head) => console.log('UPGRADE:', req.url));


app.get('/', function (req, res) {
	if (req.session.utente == undefined) {
		connected = false;
	}
	else {
		connected = true;
	}
	res.render('index', { connected: connected });
});

app.get('/error', function (req, res) {
	cod_status = req.query.cod_status;
	stringa = req.query.stringa;
	res.render('error', { cod_status: cod_status, connected: connected, stringa: stringa });
});


/* ********************************* CHAT-BOT CONFIGURATION ****************************************** */

const wss = new WebSocket.Server({ server, path: '/chat' });



/* *********************************** GOOGLE OAUTH ******************************************* */

app.get('/login', function (req, res) {
	if (req.session.utente != undefined) { //if a user is already connected
		res.redirect('/');
	}
	else {
		res.redirect("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/calendar&response_type=code&include_granted_scopes=true&state=state_parameter_passthrough_value&redirect_uri=https://localhost:3000/googlecallback&client_id=" + process.env.G_CLIENT_ID);
	}
});

app.get('/googlecallback', function (req, res) {
	if (req.query.code != undefined) {
		res.redirect('gtoken?code=' + req.query.code)
	}
	else {
		cod_status = 404;
		res.redirect('/error?cod_status=' + cod_status);
	}
});

app.get('/gtoken', function (req, res) {
	var url = 'https://www.googleapis.com/oauth2/v3/token';
	var formData = {
		code: req.query.code,
		client_id: process.env.G_CLIENT_ID,
		client_secret: process.env.G_CLIENT_SECRET,
		redirect_uri: "https://localhost:3000/googlecallback",
		grant_type: 'authorization_code'
	}

	request.post({ url: url, form: formData }, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		var info = JSON.parse(body);
		if (info.error != undefined) {
			res.send(info.error);
		}
		else {
			req.session.google_token = info.access_token;
			console.log("google token is: " + req.session.google_token);
			console.log("info is (1):")
			console.log(info);
			console.log("req.session: ")
			console.log(req.session);
			res.redirect('/register');
		}
	});

});

app.get('/register', function (req, res) {

	if (req.session.google_token == undefined) {
		//we are here only if we type in the search bar /register
		cod_status = 404;
		return res.redirect('/error?cod_status=' + cod_status);
	}

	var google_token = req.session.google_token;
	var url = 'https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=' + google_token;
	// var headers = { 'Authorization': 'Bearer ' + google_token };

	// request.get({ headers: headers, url: url }, function (error, response, body) {
	request.get({ url: url }, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		var info = JSON.parse(body);

		console.log("info is (2): ")
		console.log(info);

		/* 
		  THESE INFO ARE ORGANIZED IN THE FOLLOWING WAY:
		  {
			"id": "xx",
			"name": "xx",
			"given_name": "xx",
			"family_name": "xx",
			"link": "xx",
			"picture": "xx",
			"gender": "xx",
			"locale": "xx"
		  }
		*/

		if (info.error != undefined) {
			res.send(info.error);
		}
		else {
			// Insert this account in the database with its id, checking if it's already present
			var id = info.id;
			request({
				url: 'http://admin:admin@couchdb:5984/users/_all_docs',
				method: 'GET',
				headers: {
					'content-type': 'application/json'
				},
			}, function (error, response, body) {
				if (error) {
					console.log(error);
				} else {
					var data = JSON.parse(body);
					for (var i = 0; i < data.total_rows; i++) {
						// if this account is already registered go back to the home page, insert it in the database and go back to the home page otherwise
						if (data.rows[i].id === id) {
							req.session.utente = id; // setting the user of this session
							connected = true;
							console.log("User " + id + ", " + info.name + " already registered");
							res.render('index', { connected: connected });
							return;
						}
					}

					let user_email = info.email;

					let username = info.name;

					var body1 = {
						"id": info.id,
						"name": info.name,
						"given_name": info.given_name,
						"family_name": info.family_name,
						"picture": info.picture,
						"gender": info.gender,
						"locale": info.locale,
						"my_list": [],
						"calendar": ""
					}
					request({
						url: 'http://admin:admin@couchdb:5984/users/' + id,
						method: 'PUT',
						headers: {
							'content-type': 'application/json'
						},
						body: JSON.stringify(body1)
					}, function (error, response, body) {
						if (error) {
							console.log(error);
						}
						else {
							console.log("Registrazione di " + id + ", " + info.name + " avvenuta");
							req.session.utente = id; // setting the user of this session
							connected = true;

							// RABBITMQ + AMQP

							console.log("Should be sending email to " + username + " at " + user_email);

							// connect to RabbitMQ server
							amqplib.connect('amqp://guest:guest@rabbitmq', (err, connection) => {

								if (err) {
									console.error(err.stack);
								}

								// create channel to send message
								connection.createChannel((err, channel) => {

									if (err) {
										console.error(err.stack);
									}

									var queue = 'registration_email_queue';

									// create queue
									channel.assertQueue(queue, {
										durable: true
									}, err => {
										if (err) {
											console.error(err.stack);
										}

										// send message to be enqueued in the specified queue
										function sender(content) {
											let sent = channel.sendToQueue(queue, Buffer.from(JSON.stringify(content)), {
												persistent: true,
												contentType: 'application/json'
											});
										};

										let sent = 0;
										let toSend = 1;
										// function to send messages to queue (in this case only one message, the registration email)
										function sendNext() {
											if (sent >= toSend) {
												console.log('[RabbitMQ] All messages sent!');
												// Close connection when all messages have been sent
												return channel.close(() => connection.close());
											}
											sent++;
											console.log('[RabbitMQ] Sending message #' + sent + ' to queue "' + queue + '"');
											// send message to queue with email and username of the user who just registered (to which we want to send an email)
											sender({ email: user_email, username: username });
											// Close connection (one message sent, i.e. all messages sent in this case)
											return channel.close(() => connection.close());
										};
										sendNext();
									});
								});
							});

							// END OF RABBITMQ + AMQP 

							res.render('index', { connected: connected });

						}
					});
				}
			}
			);
		}
	});
});


/* ************************************** PROFILE ********************************************* */

app.get('/profile', function (req, res) {
	if (req.session.utente != undefined) {
		request({
			url: 'http://admin:admin@couchdb:5984/users/' + req.session.utente,
			method: 'GET',
			headers: {
				'content-type': 'application/json'
			},
		}, function (error, response, body) {
			if (error) {
				console.log(error);
			}
			else {
				info_p = JSON.parse(body);
				if (info_p.error != undefined) {
					res.send(info_p.error);
				}
				var info_utente = info_p;
				res.render('profile', { info_utente: info_utente, connected: connected, google_token: req.session.google_token });
			}
		});
	}
	else {
		res.redirect('/');
	}
});

app.get('/logout', function (req, res) {
	if (req.session.utente != undefined) {
		req.session.destroy();
		connected = false;
		res.render('index', { connected: connected });
	}
	else {
		connected = false;
		res.render('index', { connected: connected });
	}
});

app.get('/delete_account', function (req, res) {
	if (req.session.utente != undefined) {
		id = req.session.utente
		request({
			url: 'http://admin:admin@couchdb:5984/users/' + id,
			method: 'GET',
			headers: {
				'content-type': 'application/json'
			},
		}, function (error, response, body) {
			if (error) {
				console.log(error);
				res.send("ERROR");
			} else {
				rev = (JSON.parse(body))._rev;
				request({
					url: 'http://admin:admin@couchdb:5984/users/' + id + '?rev=' + rev,
					method: 'DELETE',
					headers: {
						'content-type': 'application/json'
					},
				}, function (error, response, body) {
					if (error) {
						console.log(error);
						res.send("ERROR");
					}
					else {
						req.session.destroy();
						connected = false;
						res.render('index', { connected: connected });
						return;
					}
				});
			}
		});
	}
	else {
		res.redirect('/');
	}
});

app.post('/removeFavorites', function (req, res) {
	var id_utente = req.query.id;
	var title = req.query.title;
	var recipe_id = req.query.recipe_id;
	var info_utente;
	request({
		url: 'http://admin:admin@couchdb:5984/users/' + id_utente.toString(),
		method: 'GET',
		headers: {
			'content-type': 'application/json;charset=UTF-8'
		},
	}, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		else {
			let separator = "#|§|@|§|#";
			let my_list_item = title + separator + recipe_id;
			info_utente = JSON.parse(body);
			for (var h = 0; h < info_utente.my_list.length; h++) {
				//scan the favorites list and when you find a recipe with name equal to "title", remove it from the list
				if (recipe_id != -1 && recipe_id != "-1") {
					if (info_utente.my_list[h] == my_list_item) {
						info_utente.my_list.splice(h, 1);
					}
				} else {
					if (info_utente.my_list[h] == title) {
						info_utente.my_list.splice(h, 1);
					}
				}
			}
			request({
				url: 'http://admin:admin@couchdb:5984/users/' + id_utente,
				method: 'PUT',
				headers: {
					'content-type': 'application/json;charset=UTF-8'
				},
				body: JSON.stringify(info_utente),
			}, function (error, response, body) {
				if (error) {
					console.log(error);
				}
				else {
					res.send("true");
				}
			});
		}
	});
});

app.post('/addFavorites', function (req, res) {
	var id_utente = req.query.id;
	var title = req.query.title;
	var recipe_id = req.query.recipe_id;
	var info_utente;
	request({
		url: 'http://admin:admin@couchdb:5984/users/' + id_utente.toString(),
		method: 'GET',
		headers: {
			'content-type': 'application/json;charset=UTF-8'
		},
	}, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		else {
			let separator = "#|§|@|§|#";
			let my_list_item = title + separator + recipe_id;
			info_utente = JSON.parse(body);
			info_utente.my_list.push(my_list_item);
			request({
				url: 'http://admin:admin@couchdb:5984/users/' + id_utente,
				method: 'PUT',
				headers: {
					'content-type': 'application/json;charset=UTF-8'
				},
				body: JSON.stringify(info_utente),
			}, function (error, response, body) {
				if (error) {
					console.log(error);
				}
				else {
					res.send("true");
				}
			});
		}
	});
});


/* ************************************* CHAT BOT ********************************************* */

wss.on('connection', function connection(ws) {
	ws.on('message', function incoming(data) {
		// make the first letter uppercase and the other lowercase
		var mes = data.toString();
		// mes=mes[0].toUpperCase()+mes.slice(1).toLowerCase();
		mes = mes.toLowerCase();

		console.log(mes);

		var diet_type;
		var suggested;

		var diet_list = ['glutenfree', 'ketogenic', 'vegetarian', 'lacto-vegetarian', 'ovo-vegetarian', 'vegan', 'pescetarian', 'paleo', 'primal', 'lowfodmap', 'whole30'];

		var diet_list_mappings = {

			"gluten free": "glutenfree",
			"gluten-free": "glutenfree",
			"gluten": "glutenfree",
			"glutenfree": "glutenfree",

			"ketogenic": "ketogenic",
			"keto": "ketogenic",
			"ketogenico": "ketogenic",
			"cheto": "ketogenic",

			"vegetarian": "vegetarian",

			"lacto-vegetarian": "lacto-vegetarian",
			"lacto vegetarian": "lacto-vegetarian",
			"lacto": "lacto-vegetarian",
			"lactose": "lacto-vegetarian",

			"ovo-vegetarian": "ovo-vegetarian",
			"ovo vegetarian": "ovo-vegetarian",
			"ovo": "ovo-vegetarian",
			"eggs": "ovo-vegetarian",

			"vegan": "vegan",

			"pescetarian": "pescetarian",
			"pescatarian": "pescetarian",
			"fish": "pescetarian",

			"paleo": "paleo",

			"primal": "primal",

			"lowfodmap": "lowfodmap",
			"low fodmap": "lowfodmap",
			"fodmap": "lowfodmap",

			"whole30": "whole30",
			"whole 30": "whole30",
			"whole": "whole30",
			"whole-thirty": "whole30",
			"whole thirty": "whole30",
			"whole-30": "whole30"

		};

		// check if the inserted diet is among the ones available for the upcoming searches
		var found = false;
		for (var i = 0; i < diet_list.length; i++) {
			if (mes.toLowerCase().includes(diet_list[i].toLowerCase())) {
				// Check if the message includes any words exactly equal to the diet type
				found = true;
				diet_type = diet_list[i];
				break;
			} else {
				// Check if the message includes any key of the diet_list_mappings object that is mapped to the diet type
				for (var key in diet_list_mappings) {
					if (mes.toLowerCase().includes(key)) {
						if (diet_list_mappings[key] == diet_list[i]) {
							found = true;
							diet_type = diet_list[i];
							break;
						}
					}
				}
				if (found) break;
			}
		}

		if (!found) {
			// if the inserted diet type is not correct 
			// let message = "Diet not found, I am sorry :-(. The available diets are: glutenfree, ketogenic, vegetarian, lacto-vegetarian, ovo-vegetarian, vegan, pescetarian, paleo, primal, lowfodmap and whole30.";
			let message = "Sorry, I can't help you with that...<br>You can try asking me for a specific diet, like " + diet_list.slice(0, -1).join(", ") + " or " + diet_list.slice(-1) + ".";
			ws.send(message);
		} else {
			// if the inserted diet type exists 
			// search for recipes based on the diet type
			option = {
				url: 'https://api.spoonacular.com/recipes/complexSearch?diet=' + diet_type + '&apiKey=' + process.env.SPOONACULAR_KEY + '&number=3',
			}

			request.get(option, function (error, response, body) {
				if (error) {
					console.log(error);
				} else {
					if (response.statusCode == 200) {
						var info = JSON.parse(body);
						i = Math.round(Math.random() * 2); // random number between 0 and 2 (first 3 recipes)
						suggested = info.results[i].title;
						// ws.send("I suggest you to check: "+suggested);
						// let message = "I suggest you to check: <a href='https://localhost:3000/results_title?id=" + info.results[i].id + "' style='text-decoration: none;'>" + suggested + "</a>";
						let message = "For a <b>" + diet_type[0].toUpperCase() + diet_type.slice(1) + "</b> diet, I suggest you to check <a href='https://localhost:3000/results_title?id=" + info.results[i].id + "' style='text-decoration: none;'>" + suggested + "</a>.<br><br>Feel free to ask me for anything else!";

						ws.send(message);
					}
					else {
						cod_status = response.statusCode;
						res.redirect('/error?cod_status=' + cod_status);
					}
				}
			});
		}
	});
	// Welcome message
	// let welcome_message = "Hello! Insert a diet so I can help you find recipes following that diet...";
	let welcome_message = "Hello!<br>I am RecipeCove's chatbot, I can help you find recipes based on a specific diet.<br>Feel free to ask for anything!";
	ws.send(welcome_message);
});


/* *************************************** SPOONACULAR *********************************************** */

app.post('/results_recipe', function (req, res) {
	var titolo = req.body.search;

	var option = {
		url: 'https://api.spoonacular.com/recipes/complexSearch?apiKey=' + process.env.SPOONACULAR_KEY + '&number=20&query=' + titolo,
	}

	request.get(option, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		else {
			if (response.statusCode == 200) {
				var info = JSON.parse(body);
				if (info.results.length > 0) {
					res.render("results_recipe", { info: info, connected: connected, search: titolo });
				}
				else {
					cod_status = response.statusCode;
					message = "The recipe you searched for does not exist...";
					res.redirect('/error?cod_status=' + cod_status + '&message=' + message);
				}
			}
			else {
				cod_status = response.statusCode;
				res.redirect('/error?cod_status=' + cod_status);
			}
		}
	});
});


app.get("/results_title", function (req, res) {
	var recipe_id = req.query.id;
	var recipe_name;
	var added_to_favorites = false;
	var option = {
		url: 'https://api.spoonacular.com/recipes/' + recipe_id + '/information?apiKey=' + process.env.SPOONACULAR_KEY,
	}

	request.get(option, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		else {
			if (response.statusCode == 200) {
				var info = JSON.parse(body);
				var id_utente = req.session.utente;
				if (info != undefined) {
					recipe_name = info.title;
					if (req.session.utente != undefined) {
						request({
							url: 'http://admin:admin@couchdb:5984/users/' + id_utente,
							method: 'GET',
							headers: {
								'content-type': 'application/json'
							},
						}, function (error, response, body) {
							if (error) {
								console.log(error);
							}
							else {
								info_p = JSON.parse(body);
								console.log(info_p);
								let separator = "#|§|@|§|#";
								for (var h = 0; h < info_p.my_list.length; h++) {
									if (info_p.my_list[h] == recipe_name) {
										added_to_favorites = true;
									} else if (info_p.my_list[h].includes(separator)) {
										if (info_p.my_list[h].split(separator)[1] == recipe_id) {
											added_to_favorites = true;
										}
									}
								}
								console.log(added_to_favorites);
								res.render("results_title", { info: info, info_p: info_p, id_utente: id_utente, google_token: req.session.google_token, connected: connected, added_to_favorites: added_to_favorites });
							}
						});
					}
					else {
						res.render("results_title", { info: info, id_utente: id_utente, connected: connected, added_to_favorites: added_to_favorites });
					}
				}
				else {
					cod_status = response.statusCode;
					message = "The searched recipe does not exist"
					res.redirect('/error?cod_status=' + cod_status + '&message=' + message);
				}
			}
			else {
				cod_status = response.statusCode;
				res.redirect('/error?cod_status=' + cod_status);
			}
		}
	});
});


app.get("/results_ingredient", function (req, res) {
	var ingredient_id = req.query.id;
	var option = {
		url: 'https://api.spoonacular.com/food/ingredients/' + ingredient_id + '/information?apiKey=' + process.env.SPOONACULAR_KEY + '&amount=100&unit=grams',
	}

	request.get(option, function (error, response, body) {
		if (error) {
			console.log(error);
		}
		else {
			if (response.statusCode == 200) {
				var info = JSON.parse(body);
				if (info != undefined) {
					res.render("results_ingredient", { info: info, connected: connected });
				}
				else {
					cod_status = response.statusCode;
					message = "The searched ingredient does not exist";
					res.redirect('/error?cod_status=' + cod_status + '&message=' + message);
				}
			}
			else {
				cod_status = response.statusCode;
				res.redirect('/error?cod_status=' + cod_status);
			}
		}
	});
});


/* ********************************* PORT CONFIGURATION ****************************************** */

app.use(function (req, res, next) {
	res.status(404).redirect('/error?cod_status=' + 404);
});

server.listen(port);
