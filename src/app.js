const express = require('express');
const axios = require('axios');

const app = express();
const port = 5000;

app.use(express.json());

app.post('/getNearbyPlaces', async (req, res) => {
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
});

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
