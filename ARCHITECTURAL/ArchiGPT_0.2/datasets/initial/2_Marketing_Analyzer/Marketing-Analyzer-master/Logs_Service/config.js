var config = {
    "jwtSecret" : process.env.jwtSecret,
    "jwtAlgo" : process.env.jwtAlgo,
    'database_uri' : process.env.database_uri,
    'main_database': process.env.main_database,
    'logs_database': process.env.logs_database,
    'user_profile': process.env.user_profile,
    'default_results_collection': process.env.default_results_collection,
    'likes_collection': process.env.likes_collection,
    'user_db_suffix': process.env.user_db_suffix,
    'user_logs_collection_suffix': process.env.user_logs_collection_suffix,
    'user_rec_collection_suffix': process.env.user_rec_collection_suffix,
    'user_profile_collection_suffix': process.env.user_profile_collection_suffix,
    'Inference_Service_URL': process.env.Inference_Service_URL
}

module.exports = config