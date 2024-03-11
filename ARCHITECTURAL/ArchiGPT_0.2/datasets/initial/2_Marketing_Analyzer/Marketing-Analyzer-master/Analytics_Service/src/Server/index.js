'use-strict';
const express = require('express');
const jwt = require('express-jwt');
const Router = require('./Routers/index')
const Manager = require('../Manager/index');
const config = require('../../config')

class Server{
    
    constructor(){
        this.app = express();
        this.port = 3000;
        this.Manager = new Manager(config.database_uri);
        this.router = new Router(this.Manager);
        
    }

    async init() {
        //init middleware
        this.app.use(express.json());
        this.app.use(express.urlencoded({extended: false}));
        this.app.use(express.json());
        this.app.use(jwt({secret: config.jwtSecret, algorithms: [config.jwtAlgo], requestProperty: 'auth'}).unless({path: config.jwtExcluded}))

        try{
            //init manager and routers
            await this.Manager.init(config.main_database, config.default_results_collection);
            await this.router.initRouter();
        }
        catch(err){
            throw new Error(err);
        }

        this.app.use('/', this.router.getRouter());

        // define error handlers
        // this.app.use(function (err, req, res, next) {
        //     res.status(err.statusCode || 200).json({error: err.message})
        // })

        this.app.listen(this.port, () => {
            console.log(`App listening at http://localhost:${this.port}`)
          })
    }
}

module.exports = Server