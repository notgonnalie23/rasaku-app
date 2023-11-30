const conn = require('../config/database');

const getAllFoods = () => {
    const SQLQuery = 'SELECT * FROM food';
    return conn.execute(SQLQuery);
}

module.exports = {
    getAllFoods,
}