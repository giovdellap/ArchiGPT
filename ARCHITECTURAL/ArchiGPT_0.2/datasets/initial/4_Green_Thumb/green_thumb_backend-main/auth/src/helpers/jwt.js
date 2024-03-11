const dotenv = require('dotenv');
const jwt = require('jsonwebtoken');

// get password vars from .env file
dotenv.config();


const isAuth = (req, res, next) => {
    let token = req.headers['authorization']

    jwt.verify(token, process.env.TOKEN_SECRET, (err, user) => {
        if (err) {
            return res.status(500).json({
                ok: false,
                msg: "Token invalid!",
            });
        }

        req.user = user.data;

        next();
    });
};
function generateAccessToken(username) {
    return jwt.sign({ data: username }, process.env.TOKEN_SECRET, { expiresIn: '1h' });
}



module.exports = {
    isAuth,
    generateAccessToken
};

