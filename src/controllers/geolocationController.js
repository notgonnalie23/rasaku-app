// geolocationController.js
const express = require('express');
const router = express.Router();
const { getAddressFromCoordinates } = require('../services/googleMapsService');

// Endpoint untuk mendapatkan alamat dari koordinat geografis
router.get('/geolocation', async (req, res) => {
  try {
    // menggunakan data koordinat dari permintaan
    // contoh koordinat statis untuk demonstrasi.
    const coordinates = { latitude: -7.303241199723871, longitude: 110.0044145 };

    // Mendapatkan alamat dari koordinat
    const address = await getAddressFromCoordinates(coordinates.latitude, coordinates.longitude);

    res.json({ address });
  } catch (error) {
    console.error('Error in geolocationController:', error.message);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

module.exports = router;
