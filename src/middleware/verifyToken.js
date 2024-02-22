const jwt = require('jsonwebtoken')

const verifyToken = (req, res, next) => {
    const authHeader = req.header['authorization'];
    const token = authHeader.split('')[1];
    if(token == null) return res.sendStatus(401)
    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, decoded)=> {
    if (err) return res.sendStatus(403)
    req.email = decoded.email
    next();
    })
 }

exports = verifyToken;