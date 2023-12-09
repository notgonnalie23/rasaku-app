// googleMapsService.js
const fetch = (...args) => import('node-fetch').then(({ default: fetch }) => fetch(...args));

const apiKey = 'AIzaSyCQK3Tvf9-yQpC7yBQN8u2N2O3xbLDJDkQ'; 

// mendapatkan alamat dari koordinat geografis
async function getAddressFromCoordinates(latitude, longitude) {
  const geolocationEndpoint = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${apiKey}`;

  try {
    const response = await fetch(geolocationEndpoint);
    const data = await response.json();

    if (data.status === 'OK' && data.results.length > 0) {
      const address = data.results[0].formatted_address;
      return address;
    } else {
      throw new Error('Unable to fetch address from coordinates.');
    }
  } catch (error) {
    throw new Error('Error in getAddressFromCoordinates:', error.message);
  }
}

// rekomendasikan restoran terdekat
async function recommendNearbyRestaurants(latitude, longitude, radius = 5000, type = 'restaurant') {
  const placesApiEndpoint = `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${latitude},${longitude}&radius=${radius}&type=${type}&key=${apiKey}`;

  try {
    const response = await fetch(placesApiEndpoint);
    const data = await response.json();

    if (data.status === 'OK' && data.results.length > 0) {
      const restaurants = data.results;
      return restaurants;
    } else {
      throw new Error('Unable to fetch nearby restaurants.');
    }
  } catch (error) {
    throw new Error('Error in recommendNearbyRestaurants:', error.message);
  }
}


module.exports = {
  getAddressFromCoordinates,
  recommendNearbyRestaurants,
};
