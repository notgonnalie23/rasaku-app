const express = require('express');
const app = express();
const foodRoutes = require('./routes/foods')


app.use('/foods', foodRoutes);


app.listen(4000, () => {
    console.log('server berhasil di running di port 4000');
})