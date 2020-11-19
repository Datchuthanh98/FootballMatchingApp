const admin = require('firebase-admin');
admin.initializeApp();

const triggerJoinTeam = require('./triggerJoinTeam');
const triggerBooking = require('./triggerBooking');
const triggerMatching = require('./triggerMatching');
const player = require('./player');
const team = require('./team');
const match = require('./match');
const booking = require('./booking');
const joinTeam = require('./joinTeam');

exports.createPlayer = player.createUser;
exports.getPlayerDetail= player.getPlayerDetail;
exports.getListPlayer = player.getListPlayer;
exports.getListPlayerRequest = player.getListPlayerRequest;
exports.createTeam = team.createTeam;
exports.getListTeam = team.getListTeam;
exports.getListTeamOther = team.getListTeamOther;
exports.getTeamDetail = team.getTeamDetail;
exports.getListEvaluate= team.getListEvaluate;

exports.getAllListMatch = match.getAllListMatch;
exports.getListMatchByTeam = match.getListMatchByTeam;
exports.getMatchDetail = match.getMatchDetail;
exports.getListMatchByDate = match.getListMatchByDate;
exports.getListQueueTeam = match.getListQueueTeam;
exports.getCommentMatch = match.getCommentMatch;
exports.createMatch = match.createMatch;
exports.getListMatchByField = match.getListMatchByField;
exports.getListBookingByField = booking.getListBookingByField;
exports.createBooking = booking.createBooking;

exports.acceptJoinTeam = joinTeam.acceptJoinTeam;



//notificationn
exports.triggerJoinTeam = triggerJoinTeam.triggerJoinTeam;
exports.triggerJoinTeamToAllMembers = triggerJoinTeam.triggerJoinTeamToAllMembers;
exports.triggerBooking = triggerBooking.triggerBooking;
exports.triggerAcceptBooking = triggerBooking.triggerAcceptBooking;
exports.triggerQueue = triggerMatching.triggerQueue;
