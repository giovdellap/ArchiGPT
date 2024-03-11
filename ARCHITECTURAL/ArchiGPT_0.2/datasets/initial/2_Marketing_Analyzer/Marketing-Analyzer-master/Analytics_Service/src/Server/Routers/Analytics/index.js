'use-strict';
var express = require('express');
var assert =require('assert');
const jwt = require('jsonwebtoken');

class AnalyticsRouter {
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
    router.get('/:projectName/customer/:id', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.getCustomerProfile(username, req.params.projectName, req.params.id)
            res.status(200).json(result);
        }
        catch(err){
            next(err);
        }
    })

    router.get('/:projectName/stat/products', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.getProductsAnalytics(username, req.params.projectName)
            res.status(200).json(result);
        }
        catch(err){
            next(err);
        }
    })

    router.get('/:projectName/stat/customers', async function (req,res,next){
        try{
            //get username from token
            let username = getUserFromToken(req);
            
            let result = await manager.getCustomersAnalytics(username, req.params.projectName)
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

module.exports = AnalyticsRouter