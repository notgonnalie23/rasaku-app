const express = require('express');
const {uploadImage} = require('../controller/imagesController')
const { getAllImages } = require('../controller/imagesController')
const router = express.Router();

router.post('/', uploadImage);
router.get('/getImages', getAllImages)

module.exports = router;