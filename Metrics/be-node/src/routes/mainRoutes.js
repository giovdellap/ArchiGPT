var express = require('express');
const mainController = require("../controllers/mainController")

const mainRouter = express.Router();
mainRouter.post('/start', mainController.start)

module.exports = mainRouter;