'use-strict';
var express = require('express');
var assert =require('assert');

class AuthRouter {
    constructor(manager){
        this.router = express.Router();
        this.currentManager = manager;
    }

    async initRouter(){
        //invoke paths mount function
        mountPaths(this.router, this.currentManager);
    }

    getRouter(){
        return this.router;
    }
}

function mountPaths(router, manager) {
    
    router.post('/signup', async function (req, res, next) {
        try{
            let result = await manager.signUpUser(req.body)
            res.json(result)
        }
        catch(err){
            next(err)
        }
    })

    router.post('/login', async function (req, res, next) {
        try{
            let result = await manager.loginUser(req.body.username, req.body.password)
            res.json(result)
        }
        catch(err){
            next(err)
        }
    })
}

module.exports = AuthRouter