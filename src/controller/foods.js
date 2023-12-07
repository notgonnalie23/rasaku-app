const foodModel = require('../models/foods')

const getAllFoods = async (req, res) => {
    try {
        const [data] = await foodModel.getAllFoods();

        res.json({
            message: 'Get all food berhasil',
            data: data
        })
    } catch (err) {
        res.status(500).json({
            message: 'server error',
            serverMessage: err
        })
    }
}

const getIdFoods = async (req, res) => {
    const {id_food} = req.params
    try {
        const [data] = await foodModel.getIdFoods(id_food);
        res.json({
            message: 'Get id food berhasil',
            data: {
                data,
                id_food
            }
        })
    } catch (err) {
        res.status(500).json({
            message: 'server error',
            serverMessage: err
        })
    }
}

module.exports = {
    getAllFoods,
    getIdFoods,
    
}