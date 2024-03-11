// This is your test secret API key.
const stripe = require('stripe')('sk_test_51L09gPLBjAEfulfNi5VdCxJcTcdqN25IllT3wCtRKCd1hI19NN2GoryTyhfiYWmL1zHLFg9COe3j67rpqHLDp1T400QCvrxX0m');
const express = require('express');
const app = express();
app.use(express.static('public'));

// needed modules for Database
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://172.16.230.12:27017/"

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

const YOUR_DOMAIN = 'http://localhost:8080';


// prendi il parametro del nome della persona
user = 'giordano'

// prendere json file
const fs = require('fs');




app.get('/', (req,res) => {
  res.render('index')
})


var tot_cost = 0;

app.post('/checkout', (req,res) => {
  tot_cost = (req.body.pr)*100;
  num_tick = (req.body.pl);
  res.render('checkout', {tot_cost, num_tick})
})

app.post('/create-checkout-session', async (req, res) => {
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
        myobj = {name: "giordano", cod_tickets:this.t, evento:"Evento Circo massimo", data:"26-05-2022", luogo:"Roma", tipo_ticket:"Biglietto"}
         dbo.collection("customers").insertOne(myobj, function(err, res){
              if (err) throw err;
              db.close();  
        })
      }.bind({t:a[i]}));
    }    
    num_tick = 0
    res.render('success', {a, num_ticke})
    }
})



app.get('/ticket_list', (req,res) => {

  // perform query to take all user's tikets

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
        if (user == r[i].name){
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
