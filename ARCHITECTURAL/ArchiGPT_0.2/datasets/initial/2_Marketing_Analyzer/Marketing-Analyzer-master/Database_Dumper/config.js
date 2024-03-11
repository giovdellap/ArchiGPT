import dotenv from 'dotenv'
import { dirname } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));
dotenv.config({path : __dirname +'/../Docker/.env'});

export const Config = {
    'database_uri' : process.env.database_uri,
    'main_database': process.env.main_database,
    'collection_dataset': process.env.collection_dataset,
    'collection_dataset_configs':process.env.collection_dataset_configs,
    'default_results_collection':process.env.default_results_collection,
    'auth_collection':process.env.auth_collection,
    'user_profile':process.env.user_profile
}