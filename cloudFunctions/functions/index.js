const admin = require('firebase-admin');
admin.initializeApp();

const getTodo = require('./getTodo');
const triggerJoinTeam = require('./triggerJoinTeam');

exports.getTodo = getTodo.getTodo;
exports.getTodo2 = getTodo.getTodo2;
exports.triggerJoinTeam = triggerJoinTeam.triggerJoinTeam;
exports.triggerJoinTeamToAllMembers = triggerJoinTeam.triggerJoinTeamToAllMembers;