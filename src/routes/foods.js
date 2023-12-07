const express = require('express');
const foodController = require('../controller/foods');
const router = express.Router();

router.get('/', foodController.getAllFoods);
router.get('/:id_food', foodController.getIdFoods );


module.exports = router;