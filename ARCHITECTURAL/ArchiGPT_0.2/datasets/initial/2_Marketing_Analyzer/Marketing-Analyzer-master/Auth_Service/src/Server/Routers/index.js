'use-strict';
var express = require('express');
var assert =require('assert');
const AuthRouter = require('./Authentication/index');

class Router {

    constructor(manager){
        this.router = express.Router();
        this.currentManager = manager;
        this.authRouter = new AuthRouter(manager)
    }

    async initRouter(){
        //invoke paths mount function
        this.authRouter.initRouter();

        this.router.use('/auth', this.authRouter.getRouter());
    }

    getRouter(){
        return this.router;
    }

}

module.exports = Router