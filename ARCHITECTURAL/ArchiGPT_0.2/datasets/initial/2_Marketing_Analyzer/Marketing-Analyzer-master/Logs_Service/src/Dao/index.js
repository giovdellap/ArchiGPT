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

    async getUserProjects(username){
        try{
            let result = await this.dbColl.find({username : username}).toArray();
            return result[0].projects
        }
        catch(e){
            throw new Error(e)
        }
    }

    async insert(obj){
        try {
            await this.dbColl.insertOne(obj)
          }
          catch(e){
              throw new Error(e)
          }
    }

    async UpdateRecommendation(productName, data){
        try {
            await this.dbColl.replaceOne({"name": productName}, data, {upsert: true})
          }
          catch(e){
              throw new Error(e)
          }
    }

    async getProductProfile(productName){
        try {
            let result = await this.dbColl.find({name: productName}).toArray()
            return result
          }
          catch(e){
              throw new Error(e)
          }
    }

    async UpdateProduct(productName, data){
        try {
            await this.dbColl.replaceOne({"name": productName}, data, {upsert: true})
          }
          catch(e){
              throw new Error(e)
          }
    }

}

module.exports = DatabaseCluster