var config = {
    'salt': process.env.salt,
    "jwtSecret" : process.env.jwtSecret,
    "jwtAlgo" : process.env.jwtAlgo,
    'database_uri' : process.env.database_uri,
    'auth_database': process.env.auth_database,
    'auth_collection': process.env.auth_collection,
    'user_profile': process.env.user_profile
}

module.exports = config