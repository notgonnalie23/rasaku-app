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

module.exports = {
    getAllFoods,
}