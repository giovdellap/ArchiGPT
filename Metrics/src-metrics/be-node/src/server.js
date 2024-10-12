var express = require('express');
const cors = require('cors');

const benchmarkRoutes = require('./routes/benchmarkRoutes.js');

const app = express();

app.use(express.json({ limit: '100Mb' }))
const PORT = 5002;

app.use(cors())
app.use('/metrics', benchmarkRoutes);

// GET route for root '/'
app.get('/', (req, res) => {
	res.send(`Metrics backend Server is running on port : ${PORT}`);
  });

app.listen(PORT, console.log('Server is running on port: ' + PORT));