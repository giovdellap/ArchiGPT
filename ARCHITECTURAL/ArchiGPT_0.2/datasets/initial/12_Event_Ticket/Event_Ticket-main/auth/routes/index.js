const express = require('express');
const router = express.Router();
const { ensureAuthenticated } = require('../config/checkAuth')
const { ensureAdmin } = require('../config/checkAuth')
const { ensureManager} = require('../config/checkAuth')
const { ensureUser } = require('../config/checkAuth')
const authController = require('../controllers/authController')
const { authorize } = require('../config/authorize')

const Role = require('../config/role');
//------------ Welcome Route ------------//
router.get('/', (req, res) => {
    
    res.render('welcome');
});

router.get('/navbar', (req, res) => {
    
    if (req.isAuthenticated()) {
        res.render('navbar',{name: req.user.name,managerID: req.user._id.toString()});
    }
    else{ 
        res.render('navbar');
    }
    
});

//------------ Dashboard Route ------------//
router.get('/profiloUser', ensureUser ,  (req, res) => res.render('profiloUser', {
    name: req.user.name,
    preferenza: req.user.preferenza
}));

router.get('/profiloAdmin', ensureAdmin, (req, res) => res.render('profiloAdmin', {
    name: req.user.name
}));

router.get('/profiloManager', ensureManager, (req, res) => res.render('profiloManager', {
    name: req.user.name ,
    managerID: req.user._id.toString()
}));

router.get('/grant_Auth', authController.grantAuth);

router.get('/authorizeUser', (req, res) => authorize(Role.User,req,res));

router.get('/authorizeManager', (req, res) => authorize(Role.Manager,req,res));

router.get('/authorizeAdmin', (req, res) => authorize(Role.Admin,req,res));

router.get('/Aevents',(req,res)=>{
    query=req.query;
    if (req.query.event_type) query="event_type="+req.query.event_type;
    if(req.query.event_categ) query="event_categ="+req.query.event_categ;
    if(req.isAuthenticated()){
        console.log(req.query.toString());
        var user=req.user;
        res.redirect("/events?"+query+"&name="+user.name+'&email='+user.email+'&role='+user.role);
    }
    else{
        res.redirect("/events?"+query);
    }
})
router.get('/Aprenotazioni',(req,res)=>{
    
    if(req.isAuthenticated()){
        console.log(req.query.toString());
        var user=req.user;
        res.redirect("/prenotazioni?email="+user.email);
    }
    else{
        res.redirect("/auth/login");
    }
})

router.get('/Aeventi_manager',(req,res)=>{
    
    if(req.isAuthenticated()){
        console.log(req.query.toString());
        var user=req.user;
        var id=user._id.toString();
        res.redirect("/eventi_manager?managerID="+id);
    }
    else{
        res.redirect("/auth/login");
    }
})





router.post('/setPreferenza', authController.setPreferenza)

module.exports = router;