const express = require('express')
const foodController = require('../controller/foods')
const router = express.Router();

router.get('/foods', foodController.getAllFoods);
router.get('/foods/:id_food', foodController.getFoodsById);

module.exports = router;