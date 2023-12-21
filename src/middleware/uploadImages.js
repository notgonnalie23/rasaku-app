const util = require('util');
const Multer = require('multer');
const maxSize = 2 * 1024 * 1024;

let processFile = Multer({
    storage: Multer.memoryStorage(),
    limits: { fileSize: maxSize },
}).single('file');

let processFileMiddleware = util.promisify(processFile);

// const logRequest = (req, res, next) => {
//     console.log('User meminta request', req.path);
//     next();
// }

module.exports = {
    processFileMiddleware
};