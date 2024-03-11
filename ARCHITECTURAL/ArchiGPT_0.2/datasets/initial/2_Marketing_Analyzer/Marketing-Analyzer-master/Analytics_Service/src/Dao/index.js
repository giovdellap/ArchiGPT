'use strict';
const res = require('express/lib/response');
const { MongoClient } = require('mongodb');
const path = require('path')
const DbErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Database

class DatabaseCluster{

    constructor(uri){
        this.client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true })
    }

    async connect(){
        try {
            // Connect the client to the server
            await this.client.connect();
          }
          catch(e){
              throw new Error(e)
          }
    }

    async init(databaseName, CollectionName){
        try {
            // Connect the client to the server
            await this.client.connect();
      
            // Establish and verify connection
            const database = this.client.db(databaseName);
            this.dbColl = database.collection(CollectionName);
          }
          catch(e){
              throw new Error(e)
          }
    }

    async setDatabase(databaseName, CollectionName){
        try {
            // Establish and verify connection
            const database = this.client.db(databaseName);
            this.dbColl = database.collection(CollectionName);
          }
          catch(e){
              throw new Error(e)
          }
    }

    async getAllResults(){
        try {
            let results = await this.dbColl.find({}).toArray();
            return results;
          }

        catch(e){
            let err = new Error(e)
            err.code = DbErrors.DBGET
            throw new Error(err)
          }
    }

    async getUserProjects(username){
        try{
            let result = await this.dbColl.find({username : username}).toArray();
            return result[0].projects
        }
        catch(err){
            throw new Error(e)
        }
    }

    async aggregateCustomerProfile(customerID){
        try{
            let result = await this.dbColl.aggregate([
                {
                  '$match': {
                    'ID': customerID
                  }
                }, {
                  '$project': {
                    'product': 1, 
                    '_id': 0, 
                    'num_prod': 1
                  }
                }, {
                  '$group': {
                    '_id': '$product', 
                    'Quantity': {
                      '$sum': '$num_prod'
                    }
                  }
                }
              ]).sort({Quantity: -1}).toArray();
            return result
        }
        catch(err){
            throw new Error(err)
        }
    }

    async aggregateProductsSold(){
        try{
            let result = await this.dbColl.aggregate([
                {
                  '$project': {
                    'product': 1, 
                    '_id': 0, 
                    'num_prod': 1
                  }
                }, {
                  '$group': {
                    '_id': '$product', 
                    'Quantity': {
                      '$sum': '$num_prod'
                    }
                  }
                }
              ]).sort({Quantity: -1}).toArray();
            return result
        }
        catch(err){
            throw new Error(err)
        }
    }

    async aggregateCustomersPurchases(){
        try{
            let result = await this.dbColl.aggregate([
                {
                  '$project': {
                    'ID': 1, 
                    '_id': 0, 
                    'num_prod': 1
                  }
                },
                {
                  '$group': {
                    '_id': '$ID', 
                    'Purchased': {
                      '$sum': '$num_prod'
                    }
                  }
                },
                {
                  "$sort" : { Purchased : -1 } 
                }
              ]).toArray();
            return result
        }
        catch(err){
            throw new Error(err)
        }
    }

    async insertUserProject(username, projectName){
        try{
            let result = await this.dbColl.updateOne({username : username}, {"$push": {"projects": projectName}});
            return result
        }
        catch(err){
            throw new Error(e)
        }
    }

    // This implementation is only for the mobile app project Concurrency feature
    // To be removed

    async getLikes(username, InputProject, InputProduct){
        try{
            let responseObj = {
                likes: 0,
                unLikes: 0 
            }
            let result = await this.dbColl.find({username: username, project: InputProject, product: InputProduct}).toArray()
            if(result[0] != null){
                responseObj.likes = result[0].likes
                responseObj.unLikes = result[0].unLikes
            }
            return responseObj
        }
        catch(err){
            throw new Error(err)
        }
    }

    async insertLike(username, InputProject, InputProduct){
        try{
            let result = await this.dbColl.updateOne({username: username, project: InputProject, product: InputProduct},
                {"$inc": {
                    "likes": 1,
                    "unLikes":0
                }}, {upsert: true})
            return result
        }
        catch(err){
            throw new Error(err)
        }
    }

    async insertUnLike(username, InputProject, InputProduct){
        try{
            let result = await this.dbColl.updateOne({username: username, project: InputProject, product: InputProduct},
                {"$inc": {
                    "likes": 0,
                    "unLikes":1
                }}, {upsert: true})
            return result
        }
        catch(err){
            throw new Error(err)
        }
    }



    async closeConnection(){
        await this.client.close();
    }
}

module.exports = DatabaseCluster