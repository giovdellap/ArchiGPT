const request = require('request');
const https = require('https');

const server = require('./server.js');
const { machine } = require('os');

const api_key = 'cg0fcopr01qm2n8o8jq0cg0fcopr01qm2n8o8jqg';
const api_key2 = 'cgealhhr01qvduhgkbrgcgealhhr01qvduhgkbs0';

const api_key_boxes = '329a4e4931f134c1e18c56e8c7d48ffb';
const api_key_crypto = '57a5373b-e203-43b3-8edf-41fd357308ca';

function getOneYearBeforeTimestamp(currentTimestamp) {
    const oneYearInMilliseconds = 31536000000; // number of milliseconds in one year (365 days)
    return currentTimestamp - oneYearInMilliseconds;
}

function convertTimestamp(timestamp) {
    var d = new Date(timestamp * 1000), // Convert the passed timestamp to milliseconds
        yyyy = d.getFullYear(),
        mm = ('0' + (d.getMonth() + 1)).slice(-2),  // Months are zero based. Add leading 0.
        dd = ('0' + d.getDate()).slice(-2),         // Add leading 0.
        hh = d.getHours(),
        h = hh,
        min = ('0' + d.getMinutes()).slice(-2),     // Add leading 0.
        ampm = 'AM',
        time;

    if (hh > 12) {
        h = hh - 12;
        ampm = 'PM';
    } else if (hh === 12) {
        h = 12;
        ampm = 'PM';
    } else if (hh == 0) {
        h = 12;
    }

    // ie: 2014-03-24
    time = yyyy + '-' + mm + '-' + dd+ ', ' + h + ':' + min + ' ' + ampm;
    return time;
}

exports.performGraph = function (symbol, corrId, replyTo, id) {
    var ts = Date.now();
    var link = "https://finnhub.io/api/v1/stock/candle?symbol=" + symbol + "&resolution=30"+ "&from=" +  Math.floor((Date.now() - (5 * 24 * 60 * 60 * 1000)) / 1000) +"&to=" + Math.floor(new Date().getTime() / 1000) + "&token=" + api_key;
    request(link, function (error, response, body) {
        if (error) throw new Error(error);
        server.sendToQueue(JSON.stringify(body), corrId, replyTo, id);
    });
}

exports.performStockData = function (symbol, corrId, replyTo, id) {
    const opts = {
        method: 'GET',
        json: true,
        url: "https://finnhub.io/api/v1/quote?symbol="+symbol+"&token="+api_key2
    };
    
    request(opts, function (error, response, body) {
        if (error) throw new Error(error);

        const obj = {price: (body.c).toFixed(2), change: (body.dp).toFixed(2), high: body.h.toFixed(2), low: body.l.toFixed(2)};
        server.sendToQueue(JSON.stringify(obj), corrId, replyTo, id);
    });
}

exports.performSearchCall = function (search, corrId, replyTo, id) {
    
    var result = new Array();

    console.log("Performing SEARCH");
    
    const opt = {
        method: 'GET',
        json: true,
        url: "https://financialmodelingprep.com/api/v3/search?query="+search+"&limit=20&apikey="+api_key_boxes,
    }

    request(opt, function (error, response, body) {
        if (error) throw new Error(error);
        
        for (var i=0; i<body.length; i++){
            if(body[i].exchangeShortName == "NASDAQ" || body[i].exchangeShortName == "NYSE")
                result.push(body[i]);
        }

        server.sendToQueue(JSON.stringify(result), corrId, replyTo, id);
    });
}

function GetSymbols(){
    return new Promise((resolve) => {

        console.log("Getting symbols");
        var resp = new Set();
        var symbols = ['MSFT', 'TSLA', 'GOOGL', 'XOM', 'JNJ', 'AMZN']; 

        for (var i=0; i<symbols.length; i++){
            const options = {
                method: 'GET',
                json: true,
                url: 'https://finnhub.io/api/v1/stock/peers?symbol='+symbols[i],
                headers: {
                    'X-Finnhub-Token': api_key,
                }
            };

            request(options, function (error, response, body) {
                if (error) throw new Error(error);

                for (var j=0; j<body.length; j++){
                    resp.add(body[j]);

                    if (resp.size == 25){
                        resp.add('AAPL');
                        console.log("Symbols: OK");
                        resolve(resp);
                    }
                }
            });
        }
    
    });
}

exports.performBoxes = function(corrId, replyTo, id){
    
    var result;
    var maxChange = -999;
    var best_sector;
    var maxChangeIndex = -999;
    var best_index;

    var indexes = ['DIA', 'IWM', 'QQQ', 'SPY', 'SSO'];
    var infos = new Array();

    console.log("Performing BOXES");

    const opt = {
        method: 'GET',
        json: true,
        url: 'https://financialmodelingprep.com/api/v3/sector-performance?apikey='+api_key_boxes,
    }

    request(opt, function (error, response, data) {
        if (error) throw new Error(error);

        for (var i=0; i<data.length; i++) {
            if (Number(data[i].changesPercentage.slice(0,-1)) > maxChange){
                best_sector = data[i].sector;
                maxChange = Number(data[i].changesPercentage.slice(0,-1));
            }
        }

        result = '{ "sector" : { "name": "'+best_sector+'", "change": '+maxChange.toFixed(2)+'}, ';


        for (var i=0; i<indexes.length; i++){
            const opt = {
                method: 'GET',
                json: true,
                url: 'https://finnhub.io/api/v1/quote?symbol='+indexes[i]+'&token='+api_key2,
                s: indexes[i],
            }

            request(opt, function (error, response, body) {
                if (error) throw new Error(error);

                infos.push({ symbol : opt.s, change : body.dp});

                if (infos.length == indexes.length) {
                    console.log(infos);
                    for (var j = 0; j < infos.length; j++) {
                        if (infos[j].change > maxChangeIndex) {
                            best_index = infos[j].symbol;
                            maxChangeIndex = infos[j].change;
                        }
                    }
    
                    result += '"index" : {"name": "' + best_index + '", "change": ' + maxChangeIndex.toFixed(2) + '} }';
    
                    server.sendToQueue(JSON.stringify(JSON.parse(result)), corrId, replyTo, id);
                }
            });
        }

    });

}

exports.performNews = function(corrId, replyTo, id){ 
    
    var news = new Array();

    console.log("Performing NEWS");
    
    const opt = {
        method: 'GET',
        json: true,
        url: 'https://finnhub.io/api/v1/news?category=general&token='+api_key2,
    };

    request(opt, function (error, response, data) {
        if (error) throw new Error(error);

        for (var i=0; i<data.length && i<5; i++){
            news.push({
                "category": data[i].category[0].toUpperCase()+data[i].category.slice(1),
                "datetime": convertTimestamp(data[i].datetime),
                "headline": data[i].headline[0] == ":" ? data[i].headline.slice(2) : data[i].headline,
                "image": data[i].image,
                "source": data[i].source,
                "summary": data[i].summary,
                "url": data[i].url
            });
        }

        server.sendToQueue(JSON.stringify(news), corrId, replyTo, id);
    });
}

exports.performGetData = function(corrId, replyTo, id){ 
    
    var x = 0;
    var result = new Map();
    var gainers = new Array();
    var losers = new Array();
    var tickers = new Array();

    console.log("Performing GET DATA");
    
    GetSymbols().then((Symbols) => {
        var symbols = Array.from(Symbols);
        console.log(symbols);
        
        // we can do at most 60 calls per minute
        for (var i = 0; i < symbols.length; i++) { 
            var symbol = symbols[i];

            const opt = {
                method: 'GET',
                json: true,
                url: 'https://finnhub.io/api/v1/stock/profile2?symbol='+symbol+'&token='+api_key,
            };

            request(opt, function (error, response, data) {
                if (error) throw new Error(error);

                const opts = {
                    method: 'GET',
                    json: true,
                    url: 'https://finnhub.io/api/v1/quote?symbol='+data.ticker+'&token='+api_key,
                };

                request(opts, function (error, response, body) {
                    if (error) throw new Error(error);

                    if (data != undefined && data.finnhubIndustry != undefined && data.finnhubIndustry !='N/A' && data.finnhubIndustry != ''){
                        if (result.has(data.finnhubIndustry)){
                            var value = result.get(data.finnhubIndustry);
                            value.push({name:data.ticker, markCap: data.marketCapitalization, change: body.dp});
                            result.set(data.finnhubIndustry, value);
                        }
                        else{
                            var value = new Array();
                            value.push({name: data.ticker, markCap: data.marketCapitalization, change: body.dp});
                            result.set(data.finnhubIndustry, value);
                        }
                        
                        if (body!= null && (body.dp != undefined && body.dp != null) && data!= null && data.marketCapitalization != undefined){
                            tickers.push({ name: data.ticker, change: (body.dp).toFixed(2).toString()+'%'});
                            if (body.dp < 0){
                                losers.push({name: data.ticker, price: (body.c).toFixed(2), change: (body.dp).toFixed(2).toString()+'%', high: body.h.toFixed(2), low: body.l.toFixed(2), previous: body.pc, markCap: ((data.marketCapitalization/1000).toFixed(2)).toString()+'K'});
                            }
                            else{
                                gainers.push({name: data.ticker, price: (body.c).toFixed(2), change: (body.dp).toFixed(2).toString()+'%', high: body.h.toFixed(2), low: body.l.toFixed(2), previous: body.pc, markCap: ((data.marketCapitalization/1000).toFixed(2)).toString()+'K'});
                            }
                        }
                    }
    
                    x=x+1;
    
                    // all iterations are completed 
                    if (x==26){

                        gainers.sort((a, b) => Number(b.change.slice(0, -1)) - Number(a.change.slice(0, -1)));
                        losers.sort((a, b) => Number(a.change.slice(0, -1)) - Number(b.change.slice(0, -1)));

                        // building the JSON object:
                        let data = '{ "tickers" : '+JSON.stringify(tickers)+', "gainers" : '+JSON.stringify(gainers)+', "losers" : '+JSON.stringify(losers)+', "treemap" : ';

                        // for treemap
                        let text = '{ "name": "Sectors", "children": [ ';
                        for(const x of result.entries()){
                            text += '{ "name":"'+x[0]+'", "children":'+JSON.stringify(x[1])+'},';
                        }
                        let risp = text.slice(0, -1) + ']}';

                        data += risp + '}';
    
                        server.sendToQueue(JSON.stringify(JSON.parse(data)), corrId, replyTo, id);
                    }

                });
            });
        }
    });
}

exports.performCrypto = function(corrId, replyTo, id){
    ids = "";
    ids_vec = new Array();
    most_trending = new Array();
    most_famous = new Array();
    crypto_children = new Array();

    console.log("Performing CRYPTO");

    const opt = {
        method: 'GET',
        json: true,
        url: "https://pro-api.coinmarketcap.com/v1/cryptocurrency/map?limit=25&sort=cmc_rank&CMC_PRO_API_KEY="+api_key_crypto,
    }

    request(opt, function (error, response, body) {
        if (error) throw new Error(error);

        for (var i=0; i<body.data.length; i++){
            ids += body.data[i].id+',';
            ids_vec.push(body.data[i].id);
        }
        ids = ids.slice(0, -1);
        
        const opts = {
            method: 'GET',
            json: true,
            url: "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?id="+ids+"&CMC_PRO_API_KEY="+api_key_crypto,
        }

        request(opts, function (error, response, body2) {
            if (error) throw new Error(error);

            for (var j=0; j<ids_vec.length; j++){

                var cryptos = Object.entries(body2.data).map(item => item[1]);

                crypto_children.push({
                    name: cryptos[j].name,
                    symbol: cryptos[j].symbol,
                    marketCap: cryptos[j].quote.USD.market_cap,
                    change: cryptos[j].quote.USD.percent_change_24h.toFixed(2)
                });
                
                most_trending.push({
                    rank: cryptos[j].cmc_rank,
                    name: cryptos[j].name,
                    symbol: cryptos[j].symbol,
                    price: cryptos[j].quote.USD.price.toFixed(2),
                    change1h: cryptos[j].quote.USD.percent_change_1h.toFixed(2),
                    change1d: cryptos[j].quote.USD.percent_change_24h.toFixed(2),
                    volume: ((cryptos[j].quote.USD.volume_24h)/1000).toFixed(2).toString()+'K',
                    marketCap: ((cryptos[j].quote.USD.market_cap/1000).toFixed(2)).toString()+'K',
                });

                most_famous.push({
                    rank: cryptos[j].cmc_rank,
                    name: cryptos[j].name,
                    symbol: cryptos[j].symbol,
                    in_circulation: ((cryptos[j].circulating_supply/1000000).toFixed(2)).toString()+'M',
                });
            }

            most_trending.sort((a, b) => b.change1h - a.change1h);
            most_famous.sort((a, b) => Number(b.in_circulation.slice(0,-1)) - Number(a.in_circulation.slice(0,-1)));

            var result = '{ "most_trending": '+JSON.stringify(most_trending)+', "most_famous": '+JSON.stringify(most_famous)+', "treemap": { "name":"", "children": [{"name": "Cryptocurrency", "children": '+JSON.stringify(crypto_children)+'}]}}';

            server.sendToQueue(result, corrId, replyTo, id);
        });
    });
}
