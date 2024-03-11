// This is your test secret API key.
const stripe = require('stripe')('sk_test_51L09gPLBjAEfulfNi5VdCxJcTcdqN25IllT3wCtRKCd1hI19NN2GoryTyhfiYWmL1zHLFg9COe3j67rpqHLDp1T400QCvrxX0m');
const express = require('express');
const app = express();
app.use(express.static('public'));

// needed modules for Database
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://172.16.230.12:27017/"
var url2 = "mongodb://172.16.230.16:27017/"

// necessaria per tornare i valori di hash per i codici di biglietti
var crypto = require('crypto')

app.set('view engine', 'ejs');

num_tick=0;

// Necessari per prendere i parametri in POST
var bodyParser = require('body-parser');
const { response } = require('express');
app.use(express.json());
app.use(express.urlencoded({extended: true}));
app.use('/docs', express.static('./docs'));

const YOUR_DOMAIN = 'http://localhost';


// prendi il parametro del nome della persona
var user;

// prendere json file
const fs = require('fs');

  // PARAMETRI NECESSARI: utente, evento, num_tick scelti
  var num_tick;
  var nome_event;
  var data_event;
  var city_event;
  var locale_event;
  var price_event;
  var avaliable_event;
  var user_email;
  var id_event;


var tot_cost = 0;
var num_tick;

app.get('/checkout', (req,res) => {
  
  num_tick = req.query.num_tick;
  nome_event = req.query.name_event;
  data_event = req.query.data_event;
  city_event = req.query.city_event;
  locale_event = req.query.locale_event;
  price_event = req.query.price_event;
  avaliable_event = req.query.avaliable_event;
  user_email = req.query.user_email;
  id_event = req.query.id_event;
  

  if (req.body.avaliable_event < req.body.num_tick || user_email==""){
    /***messaggio di alert => numero di biglietti non disponibile***/
    res.render('events')
  }
  else {
    tot_cost = (req.query.price_event)*100;
    num_tick = (req.query.num_tick);
    res.render('checkout', {tot_cost, num_tick})
  }
  
})

app.post('/create-checkout-session', async (req, res) => {

  num_tick = req.body.num_tick;
  tot_cost = req.body.tot_cost;

  const prod = await stripe.products.create({
    name: 'Acquisto Biglietto'
  })

  const pric = await stripe.prices.create({
    unit_amount: tot_cost,
    currency: 'eur',
    product: prod.id
  })


  const session = await stripe.checkout.sessions.create({
    line_items: [
      {
        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
        price: pric.id,
        quantity: num_tick,
      },
    ],
    mode: 'payment',
    success_url: `${YOUR_DOMAIN}/return_tickets`,
    cancel_url: `${YOUR_DOMAIN}/cancel.html`,
  });

  res.redirect(303, session.url);
});

app.get('/return_tickets', (req,res) => {
  if (num_tick == 0){

    // redirect alla pagina di home
    // provo ad accedere alla pagina di ricevuta dei biglietti
    res.redirect('/')
  }
  else {
    a = [];
    ind = 0;
    var user = user_email; 
    while(ind<num_tick){
      dd = new Date()
      h = dd.getHours();
      m = dd.getMinutes();
      s = dd.getSeconds();
      x = s+user+m+user+h+ind.toString();
      var hash = crypto.createHash('sha256').update(x).digest('base64')


      a.push(hash.substring(0, 43));
      ind ++;
    }
    num_ticke = num_tick
    for(var i = 0; i<num_tick; i++){      
      MongoClient.connect(url, function(err, db) {
        if (err) throw err;
        var dbo = db.db("db_tickets");
        myobj = {utente: this.us, cod_tickets:this.t, evento:this.eve_name, data:this.eve_data, city:this.eve_city, locality:this.eve_loc}
         dbo.collection("customers").insertOne(myobj, function(err, res){
              if (err) throw err;
              db.close();  
        })
      }.bind({t:a[i], us:user, eve_name:nome_event, eve_data:data_event, eve_city:city_event, eve_loc:locale_event}));
    }
    var remaining_tickets = avaliable_event - num_tick;
    MongoClient.connect(url2, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        var s = '6319b8959ebc1dcd7787c031';
        var ObjectId = require('mongodb').ObjectId;
        var query = new ObjectId(this.id_ev);
        dbo.collection("events").updateOne({'_id':query}, {$set : {'bigl_rimanenti':this.rem_tick}}, function (err, respon) {
          console.log(respon);
          db.close();
        })

      }.bind({id_ev:id_event, rem_tick:remaining_tickets}));

    num_tick = 0
    res.render('success', {a, num_ticke})
    }
})

app.get('/return_prevendite', (req,res) => {
  if (req.query.avaliable_event < req.query.num_tick){
    /***messaggio di alert => numero di biglietti non disponibile***/
    res.redirect('/events')
  }
  else {
    num_tick = req.query.num_tick;
    nome_event = req.query.name_event;
    data_event = req.query.data_event;
    city_event = req.query.city_event;
    locale_event = req.query.locale_event;
    price_event = req.query.price_event;
    avaliable_event = req.query.avaliable_event;
    user_email = req.query.user_email;
    id_event = req.query.id_event;
    
    console.log(user_email)

    if (num_tick == 0 || user_email=="" || typeof user_email === 'undefined' || user_email === null){

      // redirect alla pagina di home
      // provo ad accedere alla pagina di ricevuta dei biglietti
      res.redirect('/')
    }
    else {
      a = [];
      ind = 0;
      var user = req.query.user_email; 
      while(ind<num_tick){
        dd = new Date()
        h = dd.getHours();
        m = dd.getMinutes();
        s = dd.getSeconds();
        x = s+user+m+user+h+ind.toString();
        var hash = crypto.createHash('sha256').update(x).digest('base64')


        a.push(hash.substring(0, 43));
        ind ++;
      }
      num_ticke = num_tick
      for(var i = 0; i<num_tick; i++){      
        MongoClient.connect(url, function(err, db) {
          if (err) throw err;
          var dbo = db.db("db_tickets");
          myobj = {utente: this.us, cod_tickets:this.t, evento:this.eve_name, data:this.eve_data, city:this.eve_city, locality:this.eve_loc}
          dbo.collection("customers").insertOne(myobj, function(err, res){
                if (err) throw err;
                db.close();  
          })
        }.bind({t:a[i], us:user, eve_name:nome_event, eve_data:data_event, eve_city:city_event, eve_loc:locale_event}));
      }    
      var remaining_tickets = req.query.avaliable_event - req.query.num_tick;
      console.log(`Rimangono ${remaining_tickets} tickets`)
      console.log(typeof remaining_tickets)
      MongoClient.connect(url2, function(err, db) {
        if (err) throw err;
        var dbo = db.db("test");
        var s = '6319b8959ebc1dcd7787c031';
        var ObjectId = require('mongodb').ObjectId;
        var query = new ObjectId(this.id_ev);
        dbo.collection("events").updateOne({'_id':query}, {$set : {'bigl_rimanenti':this.rem_tick}}, function (err, respon) {
          console.log(respon);
          db.close();
        })

      }.bind({id_ev:id_event, rem_tick:remaining_tickets}));
      num_tick = 0
      
      res.render('success', {a, num_ticke})
    }
  }
})



app.get('/prenotazioni', (req,res) => {

  // perform query to take all user's tikets
  var user_email= req.query.email;
  let r;

  tickets = [];
  var arr_len;

  MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbo = db.db("db_tickets");

    dbo.collection("customers").find().toArray(function(err, r){
      if (err) throw err;
      db.close();
      var i = 0;
      while (r[i]){
        if (user_email == r[i].utente){
          tickets.push(r[i])
        }
        else{

        }
        i++;
      }

      arr_len = tickets.length

      x='1'
      res.render('bought_tickets', {tickets, arr_len});

      
        
    })
    
    
  });
   

})


app.listen(4242, () => console.log('Running on port 4242'));
