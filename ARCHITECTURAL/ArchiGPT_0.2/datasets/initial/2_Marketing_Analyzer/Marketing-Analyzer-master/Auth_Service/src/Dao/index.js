'use strict';
const res = require('express/lib/response');
const { MongoClient } = require('mongodb');

class AuthDatabaseCluster{

    constructor(uri){
        this.client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true })
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
        const database = this.client.db(databaseName);
        this.dbColl = database.collection(CollectionName);
    }

    async getAllResults(){
        try {
            let results = await this.dbColl.find({}).toArray();
            return results;
          }
        catch(e){
              throw new Error(e)
          }
    }

    async getCredentials(username){
        try {
            let result = await this.dbColl.find({"username": username}).toArray();
            return result;
          }
        catch(e){
              throw new Error(e)
          }
    }

    async insertCredentials(username, hashedPass){
        try {
            let result = await this.dbColl.insertOne({"username": username, "password": hashedPass})
            return result;
          }
        catch(e){
              throw new Error(e)
          }
    }

    async insertUserProfile(userProfile){
        try {
            let inputdoc = {
                "username" : userProfile.username,
                "full_name" : userProfile.full_name,
                "email" : userProfile.email,
                "projects": userProfile.projects
            }
    
            return await this.dbColl.insertOne(inputdoc)
          }
        catch(e){
              throw new Error(e)
          }
    }

    async closeConnection(){
        await this.client.close();
    }
}

module.exports = AuthDatabaseCluster