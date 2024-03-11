'use-strict';
var express = require('express');
var assert =require('assert');
const jwt = require('jsonwebtoken');

class LogsRouter {
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
    router.post('/new/:projectName', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.postNewLog(username, req.params.projectName, req.body);
            res.status(200).json(result);
        }
        catch(err){
            next(err);
        }
    })
}

function getUserFromToken(req) {
    let token = req.headers.authorization.split(' ')[1]
    return jwt.decode(token).user
}

module.exports = LogsRouter;
