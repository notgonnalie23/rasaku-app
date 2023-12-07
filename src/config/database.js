const mysql = require('mysql2');
const conn = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USERNAME,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  socketPath: `/cloudsql/${process.env.INTANCE_CONNECTION_NAME}`
});

module.exports = conn.promise();