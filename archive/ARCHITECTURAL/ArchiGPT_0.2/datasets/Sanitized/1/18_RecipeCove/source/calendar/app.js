var express = require('express');
var app = express();
var fs = require('fs');
var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
var request = require('request');
const https = require('https')
const { info } = require('console');
var { connected } = require('process');
require('dotenv').config();

const port = 3001;

app.set('view engine', 'ejs');
app.set('views', __dirname+'/views');
app.use(express.static(__dirname + '/views'));


/* ********************************* HTTPS SERVER ****************************************** */

const server = https.createServer({
  key: fs.readFileSync('security/key.pem'),
  cert: fs.readFileSync('security/cert.pem')
}, app);

server.addListener('upgrade',(req, res, head) => console.log('UPGRADE:', req.url));


/* ********************************  GOOGLE CALENDAR ***************************************** */

app.get('/add_calendar', function(req, res){
  user_id=req.query.id;
  google_token=req.query.google_token;
  
  //check if a calendar exists
  request({
    url: 'http://admin:admin@couchdb:5984/users/'+user_id,
    method: 'GET',
    headers: {
      'content-type': 'application/json'
    },
  }, function(error, response, body){
    if (!error){
      var data = JSON.parse(body);
      console.log("checking data from database");
      console.log(data);
      if(data.calendar!="") {
        //if the calendar exists, delete it
        console.log("calendar already exists, tring to delete it");
        var options = {
          url: 'https://www.googleapis.com/calendar/v3/calendars/'+data.calendar,
          method: 'DELETE',
          headers: {
            'Authorization': 'Bearer '+google_token
          }
        };
        request(options, function callback(error, response, body) {
          if (!error) {
            console.log("Removed old calendar");
          }
          else {
            console.log(error);
          }
        });
      }
      else {
        console.log("the calendar does not exist, creating a new one");
      }
      
      //creating a new calendar
      var options = {
        url: 'https://www.googleapis.com/calendar/v3/calendars',
        method: 'POST',
        headers: {
          'Authorization': 'Bearer '+google_token
        },
        body: '{"summary": "Recipe Calendar"}'
      };
      request(options, function callback(error, response, body) {
        if (!error && response.statusCode == 200) {
          var info = JSON.parse(body);
          console.log("info of the calendar just created");
          console.log(info);

          //adding the reader role to calendar
          var data = { 
            "role": "reader",
            "scope": {
              "type": "default"
            }
          };
          request({
            url: 'https://www.googleapis.com/calendar/v3/calendars/'+info.id+'/acl',
            method: 'POST',
            headers: {
              'Authorization': 'Bearer '+google_token
            },
            body: JSON.stringify(data)
          }, function callback(error, response, body) {
            if (!error) {
              var data1 = JSON.parse(body);
              console.log("info of the role applied to calendar");
              console.log(data1);
            
              //retreiving the revision code
              request({
                url: 'http://admin:admin@couchdb:5984/users/'+user_id,
                method: 'GET',
                headers: {
                  'content-type': 'application/json'
                },
              }, function(error, response, body){
                if (!error){
                  var data = JSON.parse(body);
                  console.log("info of user, including revision code");
                  console.log(data);
                
                  //adding the calendar id to database
                  var body1 = {
                    "id": data.id,
                    "name": data.name,
                    "given_name": data.given_name,
                    "family_name": data.family_name,
                    "picture": data.picture,
                    "gender": data.gender,
                    "locale": data.locale,
                    "my_list": data.my_list,
                    "calendar": info.id,
                    "_rev": data._rev
                  };
                  request({
                    url: 'http://admin:admin@couchdb:5984/users/'+user_id,
                    method: 'PUT',
                    headers: {
                      'content-type': 'application/json'
                  },
                  body: JSON.stringify(body1)
                  }, function(error, response, body){
                    if (!error){
                      var data = JSON.parse(body);
                      console.log("modified entry in database, logging results of modification");
                      console.log(data);
                      console.log("added calendar to database");
                      res.redirect('https://localhost:3000/profile');
                    }
                    else{
                      console.log(error);
                    }
                  });
                }
                else{
                  console.log(error);
                }
              });
            }
            else {
              console.log(error);
            }
          });
        }
        else {
          cod_status = response.statusCode;
          res.redirect('/error?cod_status='+cod_status);
        }
      });
    }
    else {
      console.log(error);
    }
  });
});


app.get('/delete_calendar', function(req, res){
  var user_id=req.query.id;
  var google_token=req.query.google_token;

  //check if a calendar exists
  request({
    url: 'http://admin:admin@couchdb:5984/users/'+user_id,
    method: 'GET',
    headers: {
      'content-type': 'application/json'
    },
  }, function(error, response, body){
    if (!error){
      var data = JSON.parse(body);
      console.log("checking data from database");
      console.log(data);
      if(data.calendar!="") {
        //if the calendar exists, delete it from google calendar
        var options = {
          url: 'https://www.googleapis.com/calendar/v3/calendars/'+data.calendar,
          method: 'DELETE',
          headers: {
            'Authorization': 'Bearer '+google_token
          }
        };
        request(options, function callback(error, response, body) {
        if (!error) {
          //also delete the calendar from database

          //retreive information from database
          request({
            url: 'http://admin:admin@couchdb:5984/users/'+user_id,
            method: 'GET',
            headers: {
              'content-type': 'application/json'
            },
          }, function(error, response, body){
            if (!error){
              var data = JSON.parse(body);
              console.log("info of user, including revision code");
              console.log(data);

              //removing calendar from database
              var body1 = {
                "id": data.id,
                "name": data.name,
                "given_name": data.given_name,
                "family_name": data.family_name,
                "picture": data.picture,
                "gender": data.gender,
                "locale": data.locale,
                "my_list": data.my_list,
                "calendar": "",
                "_rev": data._rev
              };
              request({
                url: 'http://admin:admin@couchdb:5984/users/'+user_id,
                method: 'PUT',
                headers: {
                  'content-type': 'application/json'
              },
              body: JSON.stringify(body1)
              }, function(error, response, body){
                if (!error){
                  var data = JSON.parse(body);
                  console.log("modified entry in database, logging results of modification");
                  console.log(data);
                  console.log("removed calendar from database");
                  res.redirect('https://localhost:3000/profile');
                }
                else {
                  console.log(error);
                }
              });
            }
            else {
              console.log(error);
            }
          });
          }
          else {
            console.log(error);
          }
        });
      }
      else {
        console.log("the calendar does not exists for this user");
        res.redirect('https://localhost:3000/profile');
      }
    }
    else {
      console.log(error);
    }
  });
});


app.get('/add_event', function(req, res) {
  user_id = req.query.id;
  google_token = req.query.google_token;
  title = req.query.title;
  connected = req.query.connected;
  res.render('add_event',  {user_id:user_id, google_token:google_token, title:title, connected:connected});
});


app.post('/add_event', function(req, res) {

  user_id = req.query.user_id;
  google_token = req.query.google_token;

  //retreive data of current calendar
  request({
    url: 'http://admin:admin@couchdb:5984/users/'+user_id,
    method: 'GET',
    headers: {
      'content-type': 'application/json'
    },
  }, function(error, response, body){
    if (!error){
      var data = JSON.parse(body);
      console.log("checking data from database");
      console.log(data);
      if(data.calendar!="") {

        //creating the new event
        var summary = req.body.summary;
        var description = req.body.description;
        var start_date = req.body.start_date;
        var end_date = req.body.end_date;
        var color = req.body.color;
        
        var body1 = { 
          "summary": summary,
          "description": description,
          "end": {
            "date": end_date,
            "timeZone": "Europe/Zurich"
          },
          "start": {
            "date": start_date,
            "timeZone": "Europe/Zurich"
          },
          "colorId": color
        };
        request({
          url: 'https://www.googleapis.com/calendar/v3/calendars/'+data.calendar+'/events',
          method: 'POST',
          headers: {
            'content-type': 'application/json',
            'Authorization': 'Bearer '+google_token
          },
          body: JSON.stringify(body1)
        }, function callback(error, response, body) {
          if (!error) {
            var info = JSON.parse(body);
            console.log("info of the newly created event");
            console.log(info);
            res.redirect('https://localhost:3000/profile');
          }
          else {
            console.log(error);
          }
          });
      }
      else {
        console.log("calendar does not exists, creating a new one, next you can re-create the event");
        res.redirect('/add_calendar');
      }
    }
    else{
      console.log(error);
    }
  });
});


/* ********************************* PORT CONFIGURATION ****************************************** */

server.listen(port);
