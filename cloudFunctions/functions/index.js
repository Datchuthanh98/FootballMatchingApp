const admin = require('firebase-admin');
admin.initializeApp();

const getTodo = require('./getTodo');
const triggerJoinTeam = require('./triggerJoinTeam');
const player = require('./player');
const team = require('./team');

exports.getTodo = getTodo.getTodo;
exports.createPlayer = player.createUser;
exports.getPlayerDetail= player.getPlayerDetail;
exports.getListPlayer = player.getListPlayer;
exports.createTeam = team.createTeam;
exports.getListTeam = team.getListTeam;
exports.getListTeamOther = team.getListTeamOther;
exports.getTeamDetail = team.getTeamDetail;
exports.triggerJoinTeam = triggerJoinTeam.triggerJoinTeam;
exports.triggerJoinTeamToAllMembers = triggerJoinTeam.triggerJoinTeamToAllMembers;