const logRequest = (req, res, next) => {
    console.log('User meminta request', req.path)
    next()
}

module.exports = logRequest