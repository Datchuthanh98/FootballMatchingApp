const admin = require('firebase-admin');
admin.initializeApp();

const getTodo = require('./getTodo');

exports.getTodo = getTodo.getTodo;
exports.getTodo2 = getTodo.getTodo2;