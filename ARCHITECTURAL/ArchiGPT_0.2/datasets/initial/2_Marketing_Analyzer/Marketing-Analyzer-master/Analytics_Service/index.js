'use-strict';
require('dotenv').config({path : __dirname +'/../Docker/.env'});

const Server = require('./src/Server/index')

async function main(){
    console.log("Service is starting ........");
    try{
        var server = new Server();
        await server.init();
    }
    catch(err){
        console.log("Service encountered the followint Error and couldn't start successfully:");
        console.log(err);
    }

}
main();