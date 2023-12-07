require('dotenv').config();
const PORT = process.env.PORT || 5000;
const express = require('express');
const app = express();
const foodRoutes = require('./routes/foods');
const middlewareLog = require('./middleware/log');


app.use(middlewareLog);
app.use(express.json());

app.use('/foods', foodRoutes);


app.listen(PORT, () => {
    console.log(`Server berjalan di port ${PORT}`);
});