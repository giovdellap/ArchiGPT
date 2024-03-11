const express = require('express');
const expressLayouts = require('express-ejs-layouts');
const mongoose = require('mongoose');
const flash = require('connect-flash');
const session = require('express-session');
const passport = require('passport');
const multer  = require('multer')

const app = express();



//------------ DB Configuration ------------//
//const db =  require('./config/key').getDb;//require('./config/key').MongoURI;
//ci va messa la url e la porta della network di docker 
const url = "mongodb://172.16.230.16:27017";
//------------ Mongo Connection ------------//
mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true, useFindAndModify: false })
    .then(() => console.log("Successfully connected to MongoDB"))
    .catch(err => console.log(err));

//------------ EJS Configuration ------------//
app.use(expressLayouts);
app.use("/assets", express.static('./assets'));
app.set('view engine', 'ejs');

//------------ Bodyparser Configuration ------------//
app.use(express.urlencoded({ extended: false }))


//------------ Connecting flash ------------//
//app.use(flash());

//------------ Global variables ------------//
//app.use(function(req, res, next) {
//  res.locals.success_msg = req.flash('success_msg');
//  res.locals.error_msg = req.flash('error_msg');
//  res.locals.error = req.flash('error');
//  next();
//});





//------------ Routes ------------//
app.use('/', require('./routes/index'));




const PORT = process.env.PORT || 3007;

app.listen(PORT, console.log(`Server running on PORT ${PORT}`));