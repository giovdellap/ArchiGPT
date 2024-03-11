'use strict';
import neatCsv from 'neat-csv';
import fs from 'fs';
import Dao from './Dao/dao.js'
import {Config} from './config.js'


async function main() {
    try{
        //Connecting to cluster
        const cluserUri = Config.database_uri;
        const databaseManager = new Dao(cluserUri);

        ////////////////// Loading Databases from local files
        var user_domain = await neatCsv(fs.createReadStream('Project_databases/user_domain.csv'));
        var defaultProducts = fs.readFileSync('Project_databases/default_products.json');
        defaultProducts = JSON.parse(defaultProducts);
        //console.log(user_domain);
        //console.log(defaultProducts['default_products']);

        //////////////////  Dump data into the database
        const databaseName = Config.main_database;
        const collectionDataset = Config.collection_dataset;
        const collectionDatasetConfig = Config.collection_dataset_configs;
        const collectionDefaultResults = Config.default_results_collection;
        const collectionUserCredentials = Config.auth_collection;
        const collectionUserProfile = Config.user_profile;
        let result = 0

        // // //Dump dataset
        console.log("Dumping dataset .....")
        await databaseManager.init(databaseName, collectionDataset)
        result = await databaseManager.insertMany(user_domain)

        // //Dump default results
        console.log("Dumping default results .....")
        await databaseManager.setDatabase(databaseName, collectionDefaultResults)
        result = await databaseManager.insertMany(defaultProducts['default_products'])

        // //Dump dataset config
        console.log("Dumping dataset config .....")
        await databaseManager.setDatabase(databaseName, collectionDatasetConfig)
        result = await databaseManager.insert({"version": 1})

        // //Dump user credentials
        console.log("Creating user credentials index .....")
         await databaseManager.setDatabase(databaseName, collectionUserCredentials)
        result = await databaseManager.createIndex({"username": 1})

        // //Dump user profiles
        console.log("Creating user profiles index .....")
        await databaseManager.setDatabase(databaseName, collectionUserProfile)
        result = await databaseManager.createIndex({"username": 1})
        

        databaseManager.close()
        console.log("Done ... Exiting .....")
    }
    catch(e){
    }
}

main();