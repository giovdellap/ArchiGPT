const Role = require('../config/role');
const User = require('../models/User');

module.exports = {authorize: function(roles = [],req,res){
    // roles param can be a single role string (e.g. Role.User or 'User') 
    // or an array of roles (e.g. [Role.Admin, Role.User] or ['Admin', 'User'])
    if (typeof roles === 'string') {
        roles = [roles];
    }
    if (!req.isAuthenticated()) {
        return res.status(401).json({ message: 'Unauthorized' });
    }
    
    if (roles.length && !roles.includes(req.user.role)) {
        // user's role is not authorized
        return res.status(401).json({ message: 'Unauthorized' });
    }

    // authentication and authorization successful
    res.status(200).send('Status: OK')
}

};