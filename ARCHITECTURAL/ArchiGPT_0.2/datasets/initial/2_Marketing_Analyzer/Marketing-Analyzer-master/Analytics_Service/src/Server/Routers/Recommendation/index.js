'use-strict';
var express = require('express');
var assert =require('assert');
const jwt = require('jsonwebtoken');

class RecommendationRouter {
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
    router.get('/:projectName', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.getrecommendationResult(username, req.params.projectName);
            res.status(200).json({recommendation: result});
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

module.exports = RecommendationRouter;
