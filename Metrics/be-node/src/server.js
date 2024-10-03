var express = require('express');
const cors = require('cors');

const mainRouter = require('./routes/mainRoutes.js');

const app = express();

app.use(express.json())
//app.use(express.urlencoded())
const PORT = 5001;

app.use(cors())
//app.use(bodyParser.urlencoded({ extended: false }));
app.use('/metrics', mainRouter);

app.listen(PORT, console.log('Server is running on port: ' + PORT));