const bcrypt = require('bcryptjs')
const User = require('../models/User')
const auth = require('../helpers/jwt')
const CTRL = {};

CTRL.register = (req, res) => {

    const { password } = req.body
    const salt = bcrypt.genSaltSync(10);

    req.body.password = bcrypt.hashSync(password, salt);
    const user = new User(req.body)

    user.save().then(user => {
        user ? res.json({ success: true, user }) : res.json({ error: 'User already exists' })
    }).catch(() => res.json({ error: 'User already exists' }))
}


CTRL.login = (req, res) => {
    const { email, password } = req.body;
    const user = User.findOne({ email }).then(
        user => {
            if (user && bcrypt.compareSync(password, user.password)) {
                const token = auth.generateAccessToken(email);
                return {
                    ...user.toJSON(), token
                }
            }
        })
        .then(user => {
            user ? res.json(user) : res.json({ error: 'Email or password is incorrect' });
        }
        )
}

CTRL.getUser = (req, res) => {
    const { userId } = req.params;
    const user = User.findById(userId).exec((err, user) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                err
            })
        }
        res.json({
            ok: true,
            user,
        });
    })
}

CTRL.updateUser = (req, res) => {
    const { userId } = req.params;
    User.findByIdAndUpdate(
        userId,
        req.body,
        { new: true },
        (err, user) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err,
                });
            }
            return res.status(201).json({
                ok: true,
                user,
            });
        }
    );
}

CTRL.updateUserInfo = (req, res) => {
    const { userId } = req.params;
    const { fullname,email, password } = req.body
    const salt = bcrypt.genSaltSync(10);

    var newPassword = bcrypt.hashSync(password, salt);
    User.findByIdAndUpdate(
        userId,
        {fullname: fullname, email: email, password: newPassword},
        { new: true },
        (err, user) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err,
                });
            }
            console.log('%o',user);
            return res.status(201).json({
                ok: true,
                user,
            });
        }
    );
}

CTRL.rateUsers = (req, res) => {
    const {userIds}= req.body;
    const {ratingValue } = req.params;
   
    User.updateMany(
        {
            _id:{ $in: userIds}
        },
        {
            $inc: { "ratingValue": ratingValue, "numberOfRatings":1 }
        },
        { new: true },
        (err, user) => {
            if (err) {
                return res.status(500).json({
                    ok: false,
                    err,
                });
            }
            console.log('%o',user);
            return res.status(201).json({
                ok: true,
                user,
            });
        }
    );
}
module.exports = CTRL;