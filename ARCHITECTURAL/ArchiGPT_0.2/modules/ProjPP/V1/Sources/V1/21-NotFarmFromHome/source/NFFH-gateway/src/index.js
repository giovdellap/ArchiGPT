import express from 'express';
import cors from "cors";

import {DEBUG_AUTH, ROUTES_AUTH, ROUTES_CLIENT, ROUTES_FARMER, ROUTES_IMAGE, ROUTES_ORDER} from "./routes.js";

import {setupProxies} from "./proxy.js";
import {default as RoutesBuilder} from './RoutesBuilder.js';
import { AUTH, CLIENT_BE, FARMER_BE, ORDER_BE, IMAGE } from './conf.js';

const app = express();

//app.use(function(req, res, next) {
//    res.header("Access-Control-Allow-Origin", "http://localhost:4200"); // update to match the domain you will make the request from
//    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//    next();
//  });

app.use(cors({
  origin: ["http://localhost:4200", "http://localhost:4201", "http://localhost:4202", "http://localhost:4203"],
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS', 'HEAD'],
  allowedHeaders: ['Origin', 'X-Requested-With', 'Content-Type', 'Accept', 'Authorization', 'token'],
  credentials: true,
  optionsSuccessStatus: 200
}));

const port = 8080;
const builder = new RoutesBuilder()

builder.addRoutes(ROUTES_AUTH, AUTH);
builder.addRoutes(ROUTES_CLIENT, CLIENT_BE);
builder.addRoutes(ROUTES_FARMER, FARMER_BE);
builder.addRoutes(ROUTES_ORDER, ORDER_BE);
builder.addRoutes(ROUTES_IMAGE, IMAGE);
builder.addRoutes(DEBUG_AUTH, AUTH);

setupProxies(app, builder.exposeRoutes());


app.listen(port, () => {
    console.log(`NFFH Gateway listening at http://0.0.0.0:${port}`)
})