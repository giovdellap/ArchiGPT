'use-strict';
var express = require('express');
var assert =require('assert');
const LogsRouter = require('./Log/index');

class Router {

    constructor(manager){
        this.router = express.Router();
        this.currentManager = manager;
        
        this.LogsRouter = new LogsRouter(manager)
    }

    async initRouter(){
        //invoke paths mount function
        this.LogsRouter.initRouter();

        this.router.use('/logs', this.LogsRouter.getRouter());
    }

    getRouter(){
        return this.router;
    }

}

module.exports = Router