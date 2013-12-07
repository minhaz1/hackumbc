var express = require('express')
, passport = require('passport')
, LocalStrategy = require('passport-local').Strategy
, mongodb = require('mongodb')
, mongoose = require('mongoose')
, scrypt = require('scrypt');

var app = express();

var DEFAULT = 3;

var s1_max = 5;
var sensor1 = DEFAULT;

// configure Express
app.configure(function() {
  app.set('views', __dirname + '/views');
  app.set('view engine', 'ejs');
  app.engine('ejs', require('ejs-locals'));
  app.use(express.logger());
  app.use(express.cookieParser());
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(express.static(__dirname));
  app.use(express.session({ secret: 'keyboard cat' })); // CHANGE THIS SECRET!
  // Remember Me middleware
  app.use( function (req, res, next) {
    if ( req.method == 'POST' && req.url == '/login' ) {
      if ( req.body.rememberme ) {
        req.session.cookie.maxAge = 2592000000; // 30*24*60*60*1000 Rememeber 'me' for 30 days
      } else {
        req.session.cookie.expires = false;
      }
    }
    next();
  });

  app.use(app.router);
  app.use(express.static(__dirname + '/../../public'));
});


app.get('/', function(req, res){
  res.render('index', { user: req.user });

});



app.get('/getparking', function(req, res){
  // var sensor_id = req.param('id');
  // var increment = req.param('inc');
      res.setHeader("Access-Control-Allow-Origin", "*");
      res.writeHead(200);
      res.write(sensor1 + " " + s1_max, "utf8");
      res.end();
});

app.get('/reset', function(req, res){
  // var sensor_id = req.param('id');
  // var increment = req.param('inc');
      res.setHeader("Access-Control-Allow-Origin", "*");
      sensor1 = DEFAULT;
      res.writeHead(200);
      res.write("sensor1 reset.", "utf8");
      res.end();

});


app.post('/updateparking', function(req, res){
    var sensor_id = req.param('id');
    var inc = req.param('inc');

    if(sensor_id == 1){
      if(inc != 0){
        sensor1 += 1;
      }
      else{
        sensor1 -= (sensor1 <= 0) ? 0: 1;
      }
    }

    res.end();
}).listen(3000, 'localhost');

 



app.listen(3000, function() {
  console.log('Express server listening on port 3000');
});

