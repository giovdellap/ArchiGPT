const express = require('express');
const app = express();
const mongoose = require('mongoose');
const cors = require('cors');

const errors = require('../src/helpers/errorHandler.js')

app.use(cors({ origin: "http://localhost:3001" })) // Default = CORS-enabled for all origins Access-Control-Allow-Origin: *!
app.use(express.json({limit: '50mb'})) // middleware for parsing application/json
app.use(express.urlencoded({limit: '50mb', extended: false })) // for parsing application/x-www-form-urlencoded


app.use('/products', require('./routes/product.routes'))
app.use('/categories', require('./routes/category.routes'))
app.use(errors.errorHandler); // middleware for error responses

// MongoDB connection, success and error event responses
const uri = "mongodb://inventory_mongo:27017";
// const uri = "mongodb://0.0.0.0:27017";
mongoose.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true });
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', () => console.log(`Connected to mongo at ${uri}`));

app.listen(3002);