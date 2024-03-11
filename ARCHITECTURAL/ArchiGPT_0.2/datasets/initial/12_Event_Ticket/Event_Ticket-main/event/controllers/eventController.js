const passport = require('passport');
const bcryptjs = require('bcryptjs');
const multer  = require('multer');
const fs = require("fs");
const path = require('path');
const https = require('http');
var upload = multer({ storage: storage })
var storage = multer.diskStorage({
    destination: function (req, file, cb) {
      cb(null, 'uploads')
    },
    filename: function (req, file, cb) {
      cb(null, file.fieldname + '-' + Date.now())
    }
})

//const Role = require('../config/role');
//------------ User Model ------------//
//const User = require('../models/User');
const Event = require('../models/Event');

//------------ Create event Handle ------------//
exports.createEventHandle = (req, res) => {
    const { name, date, city, locale, img, descrizione, categoria, tipo,num_bigl,prezzo_bigl,manager} = req.body;
    console.log(req);
    console.log(req.body);
    console.log(descrizione);
    let prezzo= prezzo_bigl;
    let bigl_rimanenti=num_bigl;
    

    const newEvent = new Event({
        name,
        descrizione,
        img,
        date,
        city,
        locale,
        manager,
        categoria,
        num_bigl,
        prezzo,
        tipo,
        bigl_rimanenti
        
    });
    newEvent.save().then(user => {
      const options = {
        hostname: 'rabbit_producer',
        port: 8081,
        path: '/produce?interest='+categoria+"&name_event="+encodeURIComponent(name),
        method: 'GET',
      };
      const requ = https.request(options, res => {
        console.log(`statusCode: ${res.statusCode}`);
      
        res.on('data', d => {
          console.log(d);
        });
      });
      
      requ.on('error', error => {
        console.error(error);
      });
      
      requ.end();
      


      res.redirect('/profiloManager');
    })
    .catch(err => console.log(err));



    //codice o chiamata rabbitmq per notificare
    
    

}
exports.updateEventHandle = (req, res) => {

}


exports.showEventsHandle = (req, res) => {
  
    let tipo_ticket=req.query.event_type;
    let cate=req.query.event_categ;
    var cutoff = new Date();
    let name= req.query.name;
    let email= req.query.email;
    let role= req.query.role;
    if (req.query.event_type == "Biglietto" || req.query.event_type == "Prevendita" || req.query.event_type == "Informativo"){
        //MongoClient.connect(url, function(err, db) {
          //if (err) throw err;
          //var dbo = db.db("eventi_db");
          var q = {tipo:tipo_ticket,date: {$gte: cutoff}}
          //dbo.collection("eventi").
          Event.find(q).then(events=>{//toArray(function(err, r){
              //if (err) throw err;
              //db.close();
              console.log(events);
              var r=events;
              var dimens = r.length;
              
              if (dimens===0){
                
                //dbo.collection("eventi").
                Event.find().then(events=>{//toArray(function(err, r){

                  //if (err) throw err;
                  //db.close();
                  var r=events
                  dimens = r.length;
                  a = []
                  for (i=0; i<dimens;i++){
                    a.push(r[i]._id.toString())
                  }
                  res.render('events', {r, dimens, a, name, email, role})
                  
                })
              }
              else{
                a = []
                for (i=0; i<dimens;i++){
                  a.push(r[i]._id.toString())
                }
                res.render('events', {r, dimens, a, name , email,role})
              }
          })
      //}.bind({tipo:req.query.event_type}));
    }
    else{
        if(req.query.event_categ){
          //console.log(req.query.event_categ);
          //MongoClient.connect(url, function(err, db) {
            //if (err) throw err;
            //var dbo = db.db("eventi_db");
            var q = {categoria:cate , date: {$gte: cutoff}}
            //dbo.collection("eventi").
            Event.find(q).then(events=>{//toArray(function(err, r){
                //if (err) throw err;
                //db.close();
                r=events;
                var dimens = events.length;
                
                if (dimens===0){
                  
                  //dbo.collection("eventi").
                  Event.find().then(events=>{//toArray(function(err, r){
                    //if (err) throw err;
                    //db.close();
                    var r=events;
                    dimens = r.length;
                    a = []
                    for (i=0; i<dimens;i++){
                      a.push(r[i]._id.toString())
                    }
                    res.render('events', {r, dimens, a, name, email,role})
                    
                  })
                }
                else{
                  a = []
                  for (i=0; i<dimens;i++){
                    a.push(r[i]._id.toString())
                  }
                  res.render('events', {r, dimens, a, name , email,role})
                }
            })
        //}.bind({cate:req.query.event_categ}));
        }
        else{
              //MongoClient.connect(url, function(err, db) {
                //if (err) throw err;
                //var dbo = db.db("eventi_db");
                //dbo.collection("eventi").
                Event.find().then(events=>{//toArray(function(err, r){
                    //if (err) throw err;
                    //db.close();
                    var r=events;
                    var dimens = r.length;
                    a = []
                    for (i=0; i<dimens;i++){
                      a.push(r[i]._id.toString())
                    }
                    res.render('events', {r, dimens, a, name, email,role})
                });
              //});
        }
    }
      
}


exports.removeEventHandle = (req, res) => {

}


exports.singleEventHandle = (req, res) => {
  var eventi_id = req.body.ev_id;
  var email=req.body.email;
  var ObjectId = require('mongodb').ObjectId;
  let query = new ObjectId(eventi_id);
  var q = {_id:query};
  Event.find(q).then( ress => {
    resqu = ress[0];
    if(!resqu) {
      res.render('/');
    }
    else {
      res.render('single_event', {layout:false,resqu,email});
    }
  })
}

exports.showEventiManager = (req, res) => {
  // perform query to take all managers events
  var id_man=req.query.managerID;
  var events=[];
  var ObjectId = require('mongodb').ObjectId;
  let query = new ObjectId(id_man);
  var q = {manager:query};
  Event.find(q).then(ress=>{
    events=ress;
    console.log(events);
    console.log(events[0]);
    console.log(events[0].name)
    console.log(events[0].city)
    res.render('eventi_manager', {layout: false,events},);
  });
}