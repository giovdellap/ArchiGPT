'use-strict';
var express = require('express');
var assert =require('assert');
const jwt = require('jsonwebtoken');

class ProjectRouter {
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
    
    router.get('/', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);

            let result = await manager.getProjects(username);
            res.status(200).json({projects: result});
        }
        catch(err){
            next(err);
        }
    })

    router.post('/new', async function (req, res, next){
        try{
            //get username from token
            let username = getUserFromToken(req);

            await manager.createNewProject(username, req.body.project)
            let apiUrl = "endpoint/" + req.body.project + "/logs/new"
            res.status(200).json({
                message: "Operation Successful",
                api: apiUrl})
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

module.exports = ProjectRouter;
