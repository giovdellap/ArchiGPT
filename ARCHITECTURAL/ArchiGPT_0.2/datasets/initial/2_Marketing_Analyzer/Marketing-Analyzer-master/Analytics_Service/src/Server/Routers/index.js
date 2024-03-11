'use-strict';
var express = require('express');
var assert =require('assert');
const RecommendationRouter = require('./Recommendation/index');
const AnalyticsRouter = require('./Analytics/index');
const ProjectRouter = require('./Project/index');
const LikesRouter = require('./Likes/index');

class Router {

    constructor(manager){
        this.router = express.Router();
        this.currentManager = manager;
        
        this.RecommendationRouter = new RecommendationRouter(manager)
        this.AnalyticsRouter = new AnalyticsRouter(manager);
        this.ProjectRouter = new ProjectRouter(manager);
        this.LikesRouter = new LikesRouter(manager);
    }

    async initRouter(){
        //invoke paths mount function
        this.RecommendationRouter.initRouter();
        this.AnalyticsRouter.initRouter();
        this.ProjectRouter.initRouter();
        this.LikesRouter.initRouter();

        this.router.use('/recommendation', this.RecommendationRouter.getRouter());
        this.router.use('/analytics', this.AnalyticsRouter.getRouter());
        this.router.use('/projects', this.ProjectRouter.getRouter());
        this.router.use('/likes', this.LikesRouter.getRouter());


    }

    getRouter(){
        return this.router;
    }

}

module.exports = Router