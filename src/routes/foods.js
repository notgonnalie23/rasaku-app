const express = require('express');
const foodController = require('../controller/foods');
const verifyToken = require('../middleware/verifyToken')
const userController = require('../controller/user');
const refreshToken = require('../controller/refreshToken');

const router = express.Router();

router.get('/', (req, res) => {
  foodController.getAllFoods(req, res);
});

router.get('/:id_food', (req, res) => {
  foodController.getIdFoods(req, res);
});
router.post('/getNearbyPlaces', (req, res) => {
  foodController.getNearbyPlaces(req, res);
});

router.get('/users', (req, res) => {
  userController.getUsers(req, res);
  verifyToken.verifyToken(req, res);
});

router.post('/users', (req, res) => {
  userController.register(req, res);
});
router.post('/login', (req, res) => {
  userController.login(req, res);
});
router.get('/token', (req, res) => {
  userController.refreshToken(req, res);
});
router.delete('/logout', (req, res) => {
  userController.logout(req, res);
});
module.exports = router;
