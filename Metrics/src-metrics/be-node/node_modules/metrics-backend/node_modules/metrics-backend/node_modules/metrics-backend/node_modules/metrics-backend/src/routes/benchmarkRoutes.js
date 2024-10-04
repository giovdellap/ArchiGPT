var express = require('express');
const benchmarkController = require("../controllers/benchmarkController")

const benchmarkRouter = express.Router();
benchmarkRouter.get('/benchmark', benchmarkController.benchmark)

module.exports = benchmarkRouter;