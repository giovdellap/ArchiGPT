//------------ Routing via Auth ------------//
module.exports = {
    ensureAuthenticated: function (req, res, next) {
        if (req.isAuthenticated()) {
            return next();
        }
        req.flash('error_msg', 'Please log in first!');
        res.redirect('/auth/login');
    },
    forwardAuthenticated: function (req, res, next) {
        if (!req.isAuthenticated()) {
            return next();
        }
        res.redirect('/dashboard');
    },

    
    ensureAdmin: function (req, res, next) {
        if (req.isAuthenticated()) {
            if(req.user.role=="Admin"){
                return next();
            }
        }
        req.flash('error_msg', 'You are not allowed to do this action!');
        res.redirect('/auth/login');
    },
    ensureManager: function (req, res, next) {
        if (req.isAuthenticated()) {
            if(req.user.role=="Manager"){
                return next();
            }
        }
        req.flash('error_msg', 'You are not allowed to do this action!');
        res.redirect('/auth/login');
    },
    ensureUser: function (req, res, next) {
        if (req.isAuthenticated()) {
            if(req.user.role=="User"){
                return next();
            }
        }
        req.flash('error_msg', 'You are not allowed to do this action!');
        res.redirect('/auth/login');
    }

};