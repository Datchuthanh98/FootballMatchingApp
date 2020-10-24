const admin = require('firebase-admin');
admin.initializeApp();

const triggerJoinTeam = require('./triggerJoinTeam');
const player = require('./player');
const team = require('./team');
const match = require('./match');
const booking = require('./booking');

exports.createPlayer = player.createUser;
exports.getPlayerDetail= player.getPlayerDetail;
exports.getListPlayer = player.getListPlayer;
exports.getListPlayerRequest = player.getListPlayerRequest;
exports.createTeam = team.createTeam;
exports.getListTeam = team.getListTeam;
exports.getListTeamOther = team.getListTeamOther;
exports.getTeamDetail = team.getTeamDetail;
exports.triggerJoinTeam = triggerJoinTeam.triggerJoinTeam;
exports.triggerJoinTeamToAllMembers = triggerJoinTeam.triggerJoinTeamToAllMembers;

exports.getAllListMatch = match.getAllListMatch;
exports.getMyListMatch = match.getMyListMatch;
exports.getMatchDetail = match.getMatchDetail;
exports.getListQueueTeam = match.getListQueueTeam;
exports.getCommentMatch = match.getCommentMatch;
exports.getListBookingByField = booking.getListBookingByField;
