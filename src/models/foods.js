const conn = require('../config/database');

const getAllFoods = () => {
    const SQLQuery = 'SELECT * FROM food';
    return conn.execute(SQLQuery);
}

const getFoodsById = () => {
    const id_food = req.params.id_food
    const SQLQuery = `SELECT * FROM food WHERE id_food = ${id_food}`;
    return conn.execute(SQLQuery)
}

module.exports = {
    getAllFoods,
    getFoodsById
}