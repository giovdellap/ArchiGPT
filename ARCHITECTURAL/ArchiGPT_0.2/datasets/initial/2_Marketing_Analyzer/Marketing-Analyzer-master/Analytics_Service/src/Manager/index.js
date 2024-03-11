'use-strict';
const res = require('express/lib/response');
const path = require('path')
const DatabaseCluster =require('../Dao/index');
const assert = require('assert')
const config = require(path.join(__dirname.split('src')[0], 'config'))
const SysErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Manager
const DbErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Database

class Manager{
    
    constructor(cluserUri){
        // this.noSqlDB = noSqlDB;
        // this.NoSqlCollection = NoSqlCollection;
        this.cluster = new DatabaseCluster(cluserUri);
    }

    async init(databaseName, CollectionName){
        try{
            await this.cluster.init(databaseName, CollectionName);
        }
        catch(err){
            throw new Error(err)
        }
    }

    async setDatabase(databaseName, CollectionName){
        try{
            await this.cluster.setDatabase(databaseName, CollectionName);
        }
        catch(err){
            throw new Error(err)
        }
    }

    async getDefaultData(){
        try{

            let result = await this.cluster.getDefaultResults();
            return result;
        }
        catch(err){
            throw new Error(err)
        }
    }


    async getProjects(username){
        await this.cluster.setDatabase(config.main_database, config.user_profile)
        try{
            assert.equal(typeof username, 'string');
            let result = await this.cluster.getUserProjects(username)
            return result;
        }
        catch(err){
            if (err.code == 'ERR_ASSERTION'){
                throw new Error(SysErrors.BADREQ)
            }
            else if (err.code == DbErrors.DBGET){
                throw new Error(SysErrors.USRNOTFOUND)
            }
        }
    }

    async getrecommendationResult(username, project){
        await this.cluster.setDatabase(config.main_database, config.user_profile)
        try{
            //Input Data Validation 
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');
            
            //Check if project Exists
            let projectExist = false
            let result = await this.cluster.getUserProjects(username)
            result.forEach(element => {
                if (element.localeCompare(project) == 0){
                    projectExist = true
                }
            });

            if(projectExist == false){
                throw new Error(SysErrors.NOPROJ)
            }
            else if (project == 'default'){
                await this.cluster.setDatabase(config.main_database, config.default_results_collection)
                result = await this.cluster.getAllResults();
                return result;
            }
            else{
                let targetDatabase = config.logs_database
                let targetCollection = username + '_' + project + config.user_rec_collection_suffix
                await this.cluster.setDatabase(targetDatabase, targetCollection)
                result = await this.cluster.getAllResults();
                
                //Check if this project has results or not
                if (result.length == 0){
                    throw new Error(SysErrors.NORES)
                }
                else{
                    return result;
                }
            }
        }
        catch(err){
            throw new Error(err)
        }
    }

    async getCustomerProfile(username, project, customerID){
        try{
            //Input Data Validation
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');
            assert.equal(typeof parseInt(customerID), 'number');

            //Check if Project Exists
            await this.isValidAnalytics(username, project)
            
            //access project logs
            let targetDatabase = config.logs_database
            let targetCollection = username + '_' + project + config.user_logs_collection_suffix
            await this.cluster.setDatabase(targetDatabase, targetCollection)

            //Get Customer Purchases
            let result = await this.cluster.aggregateCustomerProfile(parseInt(customerID))

            //Check if this project has results or not
            if (result.length == 0){
                throw new Error(SysErrors.NORES)
            }
            else{
                return {customer: customerID, purchases: result};
            }
        }
        catch(err){
            throw new Error(err)
        }
    }

    async getProductsAnalytics(username, project){
        try{
            //Input Data Validation
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');

            //Check if Project Exists
            await this.isValidAnalytics(username, project)
            
            //access project logs
            let targetDatabase = config.logs_database
            let targetCollection = username + '_' + project + config.user_logs_collection_suffix
            await this.cluster.setDatabase(targetDatabase, targetCollection)

            //Get Customer Purchases
            let result = await this.cluster.aggregateProductsSold()

            //Check if this project has results or not
            if (result.length == 0){
                throw new Error(SysErrors.NORES)
            }
            else{
                return {products_analytics: result};
            }
        }
        catch(err){
            throw new Error(err)
        }
    }

    async getCustomersAnalytics(username, project){
        try{
            //Input Data Validation
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');

            //Check if Project Exists
            await this.isValidAnalytics(username, project)
            
            //access project logs
            let targetDatabase = config.logs_database
            let targetCollection = username + '_' + project + config.user_logs_collection_suffix
            await this.cluster.setDatabase(targetDatabase, targetCollection)

            //Get Customer Purchases
            let result = await this.cluster.aggregateCustomersPurchases()

            //Check if this project has results or not
            if (result.length == 0){
                throw new Error(SysErrors.NORES)
            }
            else{
                return {customers_analytics: result};
            }
        }
        catch(err){
            throw new Error(err)
        }
    }

    async isValidAnalytics(username, projectName){
        //Check if Project Exists
        let projectExist = await this.projectExists(username, projectName)
        if(! projectExist){
            throw new Error(SysErrors.NOPROJ)
        }
        else if (projectName == 'default'){
            throw new Error(SysErrors.ANALYTICSDEF)
        }
        return true
    }

    async projectExists(username, projectName){
        //Record the log in the db
        //Set DB and Collection
        let db_name = config.main_database
        let coll_name = config.user_profile
        await this.setDatabase(db_name, coll_name)
        
        //Get user Projects
        let projects = await this.cluster.getUserProjects(username)
        return projects.includes(projectName)
    }

    async createNewProject(username, projectName){
        await this.cluster.setDatabase(config.main_database, config.user_profile)
        try{
            assert.equal(typeof username, 'string');
            assert.equal(typeof projectName, 'string');

            //Check if project Exists
            let projectExist = false
            let result = await this.cluster.getUserProjects(username)
            result.forEach(element => {
                if (element == projectName){
                    projectExist = true
                }
            });

            if(projectExist == true){
                throw new Error(SysErrors.DUBPROJ)
            }
            else if(projectName.indexOf(' ') >= 0){
                throw new Error(SysErrors.PROJIVD)
            }
            else{
                result = await this.cluster.insertUserProject(username, projectName)
            }

            return result;
        }
        catch(err){
            if (err.code == 'ERR_ASSERTION'){
                throw new Error(SysErrors.BADREQ)
            }
            else if (err.code == DbErrors.DBGET){
                throw new Error(SysErrors.USRNOTFOUND)
            }
            else{
                throw new Error(err)
            }
        }
    }

    // This implementation is only for the mobile app project Concurrency feature
    // To be removed

    async getLikes(username, project, product){
        await this.cluster.setDatabase(config.main_database, config.likes_collection)
        try{
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');
            assert.equal(typeof product, 'string');
            let result = await this.cluster.getLikes(username, project, product)
            return result;
        }
        catch(err){
            if (err.code == 'ERR_ASSERTION'){
                throw new Error(SysErrors.BADREQ)
            }
            else if (err.code == DbErrors.DBGET){
                throw new Error(SysErrors.USRNOTFOUND)
            }
            else{
                throw new Error(err)
            }
        }

    }

    async doLike(username, project, product){
        await this.cluster.setDatabase(config.main_database, config.likes_collection)
        try{
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');
            assert.equal(typeof product, 'string');
            let result = await this.cluster.insertLike(username, project, product)
            return {"message": "Operation Successful"};
        }
        catch(err){
            if (err.code == 'ERR_ASSERTION'){
                throw new Error(SysErrors.BADREQ)
            }
            else if (err.code == DbErrors.DBGET){
                throw new Error(SysErrors.USRNOTFOUND)
            }
            else{
                throw new Error(err)
            }
        }
    }

    async unLike(username, project, product){
        await this.cluster.setDatabase(config.main_database, config.likes_collection)
        try{
            assert.equal(typeof username, 'string');
            assert.equal(typeof project, 'string');
            assert.equal(typeof product, 'string');
            let result = await this.cluster.insertUnLike(username, project, product)
            return {"message": "Operation Successful"};
        }
        catch(err){
            if (err.code == 'ERR_ASSERTION'){
                throw new Error(SysErrors.BADREQ)
            }
            else if (err.code == DbErrors.DBGET){
                throw new Error(SysErrors.USRNOTFOUND)
            }
            else{
                throw new Error(err)
            }
        }
    }
}

module.exports = Manager