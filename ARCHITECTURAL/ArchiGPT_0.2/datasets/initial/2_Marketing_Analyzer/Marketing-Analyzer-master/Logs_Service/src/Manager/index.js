'use-strict';
const res = require('express/lib/response');
const path = require('path')
const DatabaseCluster =require('../Dao/index');
const Parser = require('./Parser/index')
const assert = require('assert');
const req = require('express/lib/request');
const config = require(path.join(__dirname.split('src')[0], 'config'))
const SysErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Manager
const DbErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Database
const axios = require('axios');
const defaultProfile = require(path.join(__dirname.split('src')[0], 'src' , 'Models', 'product_profile'))

class Manager{
    
    constructor(cluserUri){
        // this.noSqlDB = noSqlDB;
        // this.NoSqlCollection = NoSqlCollection;
        this.cluster = new DatabaseCluster(cluserUri);
        this.parser = new Parser();
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

    async postNewLog(username, projectName, logBody){
        //Check if Project Exists
        let projectExist = await this.projectExists(username, projectName)
        if(! projectExist){
            return {message: "Project Name Not Found"}
        }

        //Record the log in the db
        await this.recordLog(username, projectName, logBody)

        //create/update the product profile
        let productProfile = await this.incrementProductProfile(username, projectName, logBody)

        //check if inference is needed? infer-saveResults:do nothing
        let inferedSamples = productProfile['infered_samples']
        let totalSamples = productProfile['no_of_samples']

        if(inferedSamples/totalSamples < 0.75){
            //Invoke Inferer
            let productName = productProfile['name']

            let productResult = await this.inferProfile(productProfile)

            productResult['name'] = productName
            productProfile['infered_samples'] = productProfile['no_of_samples']

            //Update Customer Results
            await this.updateCustomerResult(username, projectName, productResult)

            //record updated/created product profile in database
            await this.updateProductProfile(username, projectName, productProfile)
        }
        else{
            //record updated/created product profile in database
            await this.updateProductProfile(username, projectName, productProfile)
        }

        return {operation: "Successful"}
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

    async recordLog(username, projectName, logBody){
        //Record the log in the db
        //Set DB and Collection
        let db_name = config.logs_database
        let coll_name = username + '_' + projectName  + config.user_logs_collection_suffix
        await this.setDatabase(db_name, coll_name)
        
        //Insert new Log
        this.parser.modifyLogFieldsTypes(logBody)
        await this.cluster.insert(logBody)
    }

    async incrementProductProfile(username, projectName, logBody){
        //create/update the product profile
        //Set DB and Collection
        let db_name = config.logs_database
        let coll_name = username + '_' + projectName  + config.user_profile_collection_suffix
        await this.setDatabase(db_name, coll_name)
        
        //Check if product profile exists
        let requestProfile = this.parser.marshalRequestToProfile(logBody)
        let result = await this.cluster.getProductProfile(requestProfile['name'])

        if(result.length == 0){
            let newProfile = JSON.parse(JSON.stringify(defaultProfile))
            this.parser.updateExistingProfile(newProfile, requestProfile)
            return newProfile
        }
        else{
            this.parser.updateExistingProfile(result[0], requestProfile)
            return result[0]
        }
    }

    async inferProfile(productProfile){
        let payload = {"sample" : productProfile}
        let res = await axios.post(config.Inference_Service_URL, payload);
        return res.data
    }

    async updateCustomerResult(username, projectName, productResult){
        //Record the log in the db
        //Set DB and Collection
        let db_name = config.logs_database
        let coll_name = username + '_' + projectName  + config.user_rec_collection_suffix
        await this.setDatabase(db_name, coll_name)
        
        //Insert new Log
        await this.cluster.UpdateRecommendation(productResult['name'], productResult)
    }

    async updateProductProfile(username, projectName, productProfile){
        //Record the log in the db
        //Set DB and Collection
        let db_name = config.logs_database
        let coll_name = username + '_' + projectName  + config.user_profile_collection_suffix
        await this.setDatabase(db_name, coll_name)
        
        //Insert new Log
        await this.cluster.UpdateProduct(productProfile['name'], productProfile)
    }
}

module.exports = Manager