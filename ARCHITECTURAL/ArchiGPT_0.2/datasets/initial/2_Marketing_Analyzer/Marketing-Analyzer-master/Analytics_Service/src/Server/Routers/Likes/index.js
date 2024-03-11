'use-strict';
var express = require('express');
var assert =require('assert');
const jwt = require('jsonwebtoken');

class LikesRouter {
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
    router.get('/:projectName/:productName', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.getLikes(username, req.params.projectName, req.params.productName);
            res.status(200).json(result);
        }
        catch(err){
            next(err);
        }
    })

    router.post('/:projectName/:productName/like', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.doLike(username, req.params.projectName, req.params.productName);
            res.status(200).json(result);
        }
        catch(err){
            next(err);
        }
    })

    router.post('/:projectName/:productName/unlike', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.unLike(username, req.params.projectName, req.params.productName);
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

module.exports = LikesRouter;
