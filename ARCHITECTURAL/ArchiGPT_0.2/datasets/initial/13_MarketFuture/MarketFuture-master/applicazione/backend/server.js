var express = require('express');
var app = express();
var fs = require('fs');
var request = require("request")
var bodyParser = require('body-parser')
const session = require('express-session');

app.use(bodyParser.json());       // to support JSON-encoded bodies

app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
  extended: true
}));

app.use(session({
  secret: 'token segreto',
  cookie: {}

}));

var mysql = require('mysql');
const Mysql = require('sync-mysql')
const connection = new Mysql({
  host: "db",
  database: "mysql",
  port: "3306",
  user: "root",
  password: "example"
})

function esegui2(sql) {
  var result = connection.query(sql)
  return result
}

var con = mysql.createConnection({
  host: "db",
  database: "mysql",
  port: "3306",
  user: "root",
  password: "example"
});
a = 1

con.connect(function (err) {
  if (err) throw err;
  console.log("Connected!");
});

function esegui(sql) {
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log(result);
  });
}
esegui("create table if not exists users(username text,email text,password text);")
esegui("create table if not exists stocks(nomeStock text);")
esegui("create table if not exists bannedNews(titolo text);")
esegui("create table if not exists news(title text,description text,image text,url text);")

esegui("delete from stocks")
esegui("insert into stocks(nomeStock) values ('GOOGL') ")
esegui("insert into stocks(nomeStock) values ('META') ")
esegui("insert into stocks(nomeStock) values ('AMZN') ")
esegui("insert into stocks(nomeStock) values ('TSLA') ")

esegui("delete from users")
esegui("insert into users(username,email,password) values('admin','admin@admin.com','admin')")
esegui("insert into users(username,email,password) values('lorenzo','lorenzo@gmail.com','lorenzo')")
esegui("insert into users(username,email,password) values('andrea','andrea@gmail.com','andrea')")
esegui("insert into users(username,email,password) values('giorgio','giorgio@gmail.com','giorgio')")
esegui("insert into users(username,email,password) values('luca','luca@gmail.com','luca')")

app.get("/", function (req, res) {
  if (!req.session.user) {
    res.redirect('/login');
  } else {
    res.sendFile(__dirname + "/index.html")
  }
})

app.get("/getNews", function (req, res) {
  ris = esegui2("select * from news")
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify(ris));
})



app.get("/predictor", function (req, res) {
  if (!req.session.user) {
    res.redirect('/login');
  } else {
    res.sendFile(__dirname + "/predictor.html")
  }
})

app.get("/predictor.js", function (req, res) {
  if (!req.session.user) {
    res.redirect('/login');
  } else {
    res.sendFile(__dirname + "/predictor.js")
  }
})


app.get("/index.js", function (req, res) {
  res.sendFile(__dirname + "/index.js")
})
app.get("/index.css", function (req, res) {
  res.sendFile(__dirname + "/index.css")
})
app.get("/listaStocks.js", function (req, res) {
  res.sendFile(__dirname + "/listaStocks.js")
})
app.get("/blog.css", function (req, res) {
  res.sendFile(__dirname + "/blog.css")
})
app.get("/login", function (req, res) {
  res.sendFile(__dirname + "/login.html")
})
app.get("/login.css", function (req, res) {
  res.sendFile(__dirname + "/login.css")
})
app.get("/predictor.css", function (req, res) {
  res.sendFile(__dirname + "/predictor.css")
})
app.get("/admin.js", function (req, res) {
  res.sendFile(__dirname + "/admin.js")
})

app.post('/registrami', async (req, res) => {
  dati = req.body
  username = dati["username"]
  email = dati["email"]
  password = dati["password"]
  query = "insert into users(username,email,password) values('" + username + "','" + email + "','" + password + "')"
  risultati = esegui2(query)
  res.redirect('/login')
});

app.post('/loggami', async (req, res) => {
  try {
    dati = req.body
    email = dati["email"]
    password = dati["password"]
    query = "select username,email from users where email='" + email + "' and password='" + password + "'"
    risultati = esegui2(query)
    //console.log(risultati)

    if (risultati.length > 0) {
      req.session.user = {
        email: risultati[0].email,
        name: risultati[0].username
      };
      res.redirect('/');
    } else {
      res.redirect("/login")
    }
  } catch (err) {
    res.sendStatus(500);
  }
});

app.get('/sloggami', (req, res) => {
  if (req.session.user) {
    delete req.session.user;
    res.redirect('/login');
  } else {
    res.redirect('/login');
  }
});

app.get("/admin", function (req, res) {
  if (!req.session.user) {
    res.redirect('/login');
  } else if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    res.sendFile(__dirname + "/admin.html")
  }
})
app.post("/deleteUser", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    email = req.body.email
    esegui2("delete from users where email = '" + email + "'")
    res.redirect("/admin")
  }
})

app.post("/deleteUserFromUsername", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    username = req.body.username
    esegui2("delete from users where username = '" + username + "'")
    res.redirect("/admin")
  }
})

app.get("/numberOfUsers", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    dati = esegui2("select count(distinct username) as conta from users")
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(dati));
  }
})
app.get("/getUsers", function (req, res) {
  ris = esegui2("select username from users")
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify(ris));
})



app.get("/getStocksList", function (req, res) {
  ris = esegui2("select nomeStock from stocks")
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify(ris));
})



app.post("/deleteStockFromName", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    nome = req.body.nomeStock
    esegui2("delete from stocks where nomeStock  = '" + nome + "'")
    res.redirect("/admin")
  }
})

app.post("/addStock", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    nome = req.body.nomeStock
    if (nome.length > 0) {
      esegui2("insert into stocks(nomeStock) values ('" + nome + "') ")

    }
    res.redirect("/admin")
  }
})

app.get("/session", function (req, res) {
  sessione = req.session.user;
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify(sessione))
})


app.get("/bannedNews", function (req, res) {
  ris = esegui2("select titolo from bannedNews")
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify(ris));
})

app.post("/addBannedNews", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    nome = req.body.titolo
    if (nome.length > 0) {
      esegui2("insert into bannedNews(titolo) values ('" + nome + "') ")

    }
    res.redirect("/")
  }
})

app.post("/deleteBannedNews", function (req, res) {
  if (req.session.user["name"] != "admin") {
    res.redirect('/');
  } else {
    nome = req.body.titolo
    esegui2("delete from bannedNews where titolo  = '" + nome + "'")
    res.redirect("/admin")
  }
})

app.listen(3001);



