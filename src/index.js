require('dotenv').config();
const PORT = process.env.PORT || 4000;
const express = require('express');
const cors = require('cors')
const app = express();
const foodRoutes = require('./routes/foods');
const imagesRoute = require('./routes/imagesRoute')
const bodyParser = require('body-parser')


app.use(cors())
app.use(express.json());
app.use(bodyParser.json());

app.use('/foods', foodRoutes);
app.use('/uploadImage', imagesRoute);


app.listen(PORT, () => {
    console.log(`Server berjalan di port ${PORT}`);
});