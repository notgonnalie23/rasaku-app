require('dotenv').config();
const PORT = process.env.PORT || 5000;
const express = require('express');
const app = express();
const conn = require('./config/database');
const foodRoutes = require('./routes/foods');
const cookieParser = require('cookie-parser')
const cors = require('cors')
const middlewareLog = require('./middleware/log');

app.use(middlewareLog);
app.use(cookieParser());
app.use(cors({ credentials:true, origin:'http:/localhost:5000'}));
app.use(express.json());

async function startServer() {
  try {
    await conn.authenticate();
    console.log('Database terkoneksi..');
    // await Users.sync();
    
    app.use('/foods', foodRoutes);
    // app.use('/users', UserRoutes);

    app.listen(PORT, () => {
      console.log(`Server berjalan di port ${PORT}`);
    });
  } catch (error) {
    console.error(error);
  }
}

startServer();
