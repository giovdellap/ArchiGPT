var express = require('express');
const benchmarkController = require("../controllers/benchmarkController")

const benchmarkRouter = express.Router();
benchmarkRouter.post('/benchmark', benchmarkController.benchmark)
benchmarkRouter.post('/projBenchmark', benchmarkController.projBenchmark)

module.exports = benchmarkRouter;