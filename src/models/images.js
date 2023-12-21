const conn = require('../config/database');

const uploadImage = async (url_images) => {
    try{
        const SQLQuery = 'INSERT INTO image_req (url_images) VALUES (?)';
        const [data] = await conn.execute(SQLQuery, [url_images]);
        return data;
    } catch (err) {
        throw err;
    }
}


module.exports = {uploadImage};