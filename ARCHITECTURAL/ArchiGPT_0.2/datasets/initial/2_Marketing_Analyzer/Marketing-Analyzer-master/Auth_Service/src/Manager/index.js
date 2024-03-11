'use-strict';
const path = require('path')
const bcrypt = require('bcrypt');
const assert = require('assert')
const SysErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Manager
const DBErrors = require(path.join(__dirname.split('src')[0], 'src' , 'SystemErrors')).Database
const jwt = require('jsonwebtoken');
const config = require(path.join(__dirname.split('src')[0], 'config'))
const AuthDatabaseCluster = require('../Dao/index')

class Manager {
    constructor(cofigData){
        
        this.databaseUri = cofigData.database_uri;
        this.databaseName = cofigData.auth_database;
        this.userCredentialsCollection = cofigData.auth_collection;
        this.userProfileCollection = cofigData.user_profile;


        this.dbNoSQL = new AuthDatabaseCluster(this.databaseUri);
    }

    async init(){
        await this.dbNoSQL.init(this.databaseName, this.userCredentialsCollection);
    }

    async signUpUser(userProfile){
        try{
            assert.equal(typeof userProfile.username, 'string');
            assert.equal(typeof userProfile.password, 'string');
            assert.equal(typeof userProfile.full_name, 'string');
            assert.equal(typeof userProfile.email, 'string');
        }
        catch(err){
            throw new Error(SysErrors.BADREQ);
        }

        this.dbNoSQL.setDatabase(this.databaseName, this.userCredentialsCollection)

        let userQuery = await this.dbNoSQL.getCredentials(userProfile.username);

        if(userQuery.length > 0){
            throw new Error(SysErrors.USREXIST)
        }
        else{
            try{
                let hashedPass = bcrypt.hashSync(userProfile.password, config.salt);
                await this.dbNoSQL.insertCredentials(userProfile.username, hashedPass);
            }
            catch(err){
                throw new Error(DBErrors.DBINSERT)
            }
        }

        await this.createUserProfile(userProfile)

        let token = await this.loginUser(userProfile.username, userProfile.password);
        return token;
    }

    async createUserProfile(userProfile){
        try{
            this.dbNoSQL.setDatabase(this.databaseName, this.userProfileCollection)

            userProfile.projects = ['default']
            
            await this.dbNoSQL.insertUserProfile(userProfile)
        }
        catch(err){
            throw new Error(SysErrors.BADREQ)
        }
    }

    async loginUser(username, password){
        try{
            assert.equal(typeof username, 'string');
            assert.equal(typeof password, 'string');
        }
        catch(err){
            throw new Error(SysErrors.INVALUSRPWD);
        }

        this.dbNoSQL.setDatabase(this.databaseName, this.userCredentialsCollection)

        let userQuery = await this.dbNoSQL.getCredentials(username);
        if (userQuery.length == 0){
            throw new Error(SysErrors.USRNOTFOUND)
        }
        else{
            let passCheck = bcrypt.compareSync(password, userQuery[0].password)
            if (passCheck){
                let token = jwt.sign({user: username}, config.jwtSecret)
                return {jwt: token}
            }
            else{
                throw new Error(SysErrors.PWDINVALID)
            }
        }
    }
}

module.exports = Manager