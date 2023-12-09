// app.js
const express = require('express');
const geolocationController = require('./controllers/geolocationController');
const { getAddressFromCoordinates, recommendNearbyRestaurants } = require('./services/googleMapsService');
const { formatDate } = require('./utils/helperFunctions');

const app = express();
const port = process.env.PORT || 3000;

app.use('/api', geolocationController);


const coordinates = { latitude: -7.303241199723871, longitude: 110.0044145 };

// mendapatkan daftar restoran terdekat
recommendNearbyRestaurants(coordinates.latitude, coordinates.longitude)
  .then((restaurants) => {
    console.log('Rekomendasi Restoran Terdekat:', restaurants);
  })
  .catch((error) => {
    console.error('Error:', error.message);
  });

// mendapatkan alamat dari koordinat
getAddressFromCoordinates(coordinates.latitude, coordinates.longitude)
  .then((address) => {
    console.log('Alamat dari koordinat:', address);
  })
  .catch((error) => {
    console.error('Error:', error.message);
  });

const currentDate = new Date();
const formattedDate = formatDate(currentDate);
console.log('Tanggal diformat:', formattedDate);

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
