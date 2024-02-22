const sequelize = require('../config/database');
const { DataTypes } = require('sequelize');

const Users = sequelize.define('users', {
  name: {
    type: DataTypes.STRING
  },
  email: {
    type: DataTypes.STRING
  },
  password: {
    type: DataTypes.STRING
  },
  refresh_token: {
    type: DataTypes.TEXT
  }
}, {
  freezeTableName: true
});

module.exports = Users;
