const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();
const auth = admin.auth();



exports.triggerQueue = functions.firestore.document('QueueTeam/{requestId}').onCreate(async (snap, context) => {
    const newValue = snap.data();
    // get team
    const matchId = newValue.idMatch;
    const matchRecord = await db.collection('Match').doc(matchId).get();
    const matchData = matchRecord.data();
    const bookingRecord = await db.collection('Booking').doc(matchData.idBooking).get();
    const bookingData = bookingRecord.data();


    // get player
    const idTeamHome =bookingData.idTeamHome;
    const teamRecord = await db.collection('Team').doc(idTeamHome).get();
    const teamData = teamRecord.data();

    const captainPlayerRecord =  await db.collection('Player').doc(teamData.idCaptain).get();
    const captainPlayerData = captainPlayerRecord.data();
    // get captain
    // console.log(newValue);
   
    const teamAwayRecord = await db.collection('Team').doc(newValue.idTeam).get();
    const teamAwayData = teamAwayRecord.data();

    const message = {
        data: {
            messageType: "New QueueTeam",
            nameTeamHome :  teamData.name,
            nameTeamAway :  teamAwayData.name    
        },
        token: captainPlayerData.registrationToken
    }
    admin.messaging().send(message).then((response) => {
        console.log('Successfully sent message: ', response);
        return;
    }).catch((error) => {
        console.log('Error sending message: ', error);
        return;
    })
});


