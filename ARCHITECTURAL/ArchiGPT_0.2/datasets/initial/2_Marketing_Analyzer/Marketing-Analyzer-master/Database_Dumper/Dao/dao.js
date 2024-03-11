'use strict';

import { MongoClient} from 'mongodb';

export default class Dao{

    constructor(uri){
        this.client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true })
    }

    async init(db, collectionName){
        try {
            // Connect the client to the server
            await this.client.connect();
            // Establish and verify connection
            const database = this.client.db(db);
            this.dbColl = database.collection(collectionName);
          }
          catch(e){
              console.log(e);
          }
    }

    async insertMany(docArray){
        try{
            const result = await this.dbColl.insertMany(docArray)
            return result
        }
        catch(e){
            console.log(e)
        }
    }

    async insert(doc){
        try{
            const result = await this.dbColl.insertOne(doc)
            return result
        }
        catch(e){
            console.log(e)
        }
    }

    async createIndex(index){
        try{
            const result = await this.dbColl.createIndex(index)
            return result
        }
        catch(e){
            console.log(e)
        }
    }

    async close(){
        try{
            await this.client.close();
        }
        catch(e){
            console.log(e);
        }
    }

    async setDatabase(db, collectionName){
        try{
            const database = this.client.db(db);
            this.dbColl = database.collection(collectionName);
        }
        catch(e){
            console.log(e);
        }
    }
}