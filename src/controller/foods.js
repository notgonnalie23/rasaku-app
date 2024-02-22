const foodModel = require('../models/foods');
const axios = require('axios');

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
        });
    };
};

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
        });
    } catch (err) {
        res.status(500).json({
            message: 'server error',
            serverMessage: err
        });
    };
};

const getNearbyPlaces = async (req, res) => {
    try {
        const { latitude, longitude, keyword } = req.body;
        const apiKey = 'AIzaSyCQK3Tvf9-yQpC7yBQN8u2N2O3xbLDJDkQ';
        const radius = 30000; // Radius pencarian dalam meter
        const types = 'restaurant';
    
        const placesApiUrl = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=${radius}&types=${types}&keyword=${keyword}&key=${apiKey}`;
        
        const response = await axios.get(placesApiUrl);
        const places = response.data.results;
     
        // rekomendasi 10 tempat berdasarkan lokasi
        const recommendedPlaces = places.slice(0, 10);
    
        res.json({ recommendedPlaces });
      } catch (error) {
        console.error('Error:', error.message);
        res.status(500).json({ error: 'Internal Server Error' });
      }
}

module.exports = {
    getAllFoods,
    getIdFoods, 
    getNearbyPlaces
};