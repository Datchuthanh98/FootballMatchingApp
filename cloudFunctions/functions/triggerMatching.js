const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();
const auth = admin.auth();


// gửi thông báo đến đội trưởng của sân nhà là có team X đặt sân 
exports.triggerQueue = functions.firestore.document('Match/{idMatch}/listQueueTeam/{requestId}').onCreate(async (snap, context) => {
    const newValue = snap.data();
    const idTeamAway = newValue.team;
    const idMatch = context.params.idMatch;
    const matchRecord = await db.collection('Match').doc(idMatch).get();
    const idBooking = matchRecord.data().idBooking;
    const bookingRecord = await db.collection('Booking').doc(idBooking).get();
    // const matchData = matchRecord.data();
    const bookingData = bookingRecord.data();

    // get player
    const idTeamHome = bookingData.idTeamHome;
    const teamRecord = await db.collection('Team').doc(idTeamHome).get();
    const teamData = teamRecord.data();

    const captainPlayerRecord =  await db.collection('Player').doc(teamData.idCaptain).get();
    const captainPlayerData = captainPlayerRecord.data();
    // get captain
    // console.log(newValue);
   
    const teamAwayRecord = await db.collection('Team').doc(idTeamAway).get();
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


