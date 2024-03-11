'use-strict';
const express = require('express');
const jwt = require('express-jwt');
const Router = require('./Routers/index')
const Manager = require('../Manager/index');
const config = require('../../config')

class Server{
    
    constructor(){
        this.app = express();
        this.port = 5000;
        this.DatabaseConfig = {
            "database_uri" : config.database_uri,
            "auth_database" : config.auth_database,
            "auth_collection" : config.auth_collection,
            "user_profile" : config.user_profile
        }
        this.Manager = new Manager(this.DatabaseConfig);
        this.router = new Router(this.Manager);
        
    }

    async init() {
        //init middleware
        this.app.use(express.json());
        this.app.use(express.urlencoded({extended: false}));
        this.app.use(express.json());
        //this.app.use(jwt({secret: config.jwtSecret, algorithms: [config.jwtAlgo], requestProperty: 'auth'}).unless({path: config.jwtExcluded}))

        //init manager and routers
        await this.Manager.init();
        await this.router.initRouter();
        this.app.use('/', this.router.getRouter());

        //define error handlers
        this.app.use(function (err, req, res, next) {
            res.status(err.statusCode || 200).json({error: err.message})
        })

        this.app.listen(this.port, () => {
            console.log(`App listening at http://localhost:${this.port}`)
          })
    }
}

module.exports = Server