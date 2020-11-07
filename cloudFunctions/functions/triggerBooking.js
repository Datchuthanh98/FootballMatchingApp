const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();
const auth = admin.auth();


exports.triggerBooking = functions.firestore.document('Booking/{requestId}').onCreate(async (snap, context) => {
    const newValue = snap.data();
    console.log(context.params.requestId);
    // get team
    const idField = newValue.idField;
    const fieldRecord = await db.collection('Field').doc(idField).get();
    const fieldData = fieldRecord.data();
    // get player
    const idTeam = newValue.idTeamHome;
    const teamRecord = await db.collection('Team').doc(idTeam).get();
    const teamData = teamRecord.data();
    // get captain


    const message = {
        data: {
            messageType: "NewBooking",
            idTeam: idTeam,
            nameTeam: teamData.name
        },
        token: fieldData.registrationToken
    }
    admin.messaging().send(message).then((response) => {
        console.log('Successfully sent message: ', response);
        return;
    }).catch((error) => {
        console.log('Error sending message: ', error);
        return;
    })
});

exports.triggerAcceptBooking = functions.firestore.document('Booking/{recordId}').onUpdate(async (change, context) => {
    const bookingAfter = change.after.data();
    const bookingBefore = change.before.data();
     
    const field = await db.collection("Field").doc(bookingAfter.idField).get();
    const timeGame = await db.collection("TimeGame").doc(bookingAfter.idTimeGame).get();
    const teamHome = await db.collection("Team").doc(bookingAfter.idTeamHome).get();
    let teamMemberRegistrationTokens = [];
    let teamMemberRecords = await db.collection('TeamMember').where('idTeam', '==', bookingAfter.idTeamHome).get();
    if (!teamMemberRecords.empty) {
        const teamMembersPromises = [];
        let i;
        for (i = 0 ; i < teamMemberRecords.docs.length; i++){
            const teamMemberData = teamMemberRecords.docs[i].data();
            teamMembersPromises.push(db.collection('Player').doc(teamMemberData.idPlayer).get());
        }
        await Promise.all(teamMembersPromises).then((playerRecords) => {
            playerRecords.forEach((playerRecord) => {
                const playerData = playerRecord.data();
                if (playerRecord.get('registrationToken') !== null){
                    teamMemberRegistrationTokens.push(playerData.registrationToken);
                }
            });
            return;
        });
    }

    let messageToOthers ;
    //send Accept Booking
    if(bookingAfter.approve !== bookingBefore.approve){
        messageToOthers = {
            data: {
                messageType: 'Accept Booking',
                teamName: teamHome.data().name,
                fieldName: field.data().name, 
                startTime :  timeGame.data().startTime,    
                endTime : timeGame.data().endTime
            },
            tokens: teamMemberRegistrationTokens
        }
    
    }else if(bookingAfter.idTeamAway !== bookingBefore.idTeamAway ){
     //Send Matching Team
     const teamAway = await db.collection("Team").doc(bookingAfter.idTeamAway).get();
     teamMemberRecords = await db.collection('TeamMember').where('idTeam', '==', bookingAfter.idTeamAway).get();
    if (!teamMemberRecords.empty) {
        const teamMembersPromises = [];
        let i;
        for (i = 0 ; i < teamMemberRecords.docs.length; i++){
            const teamMemberData = teamMemberRecords.docs[i].data();
            teamMembersPromises.push(db.collection('Player').doc(teamMemberData.idPlayer).get());
        }
        await Promise.all(teamMembersPromises).then((playerRecords) => {
            playerRecords.forEach((playerRecord) => {
                const playerData = playerRecord.data();
                if (playerRecord.get('registrationToken') !== null){
                    teamMemberRegistrationTokens.push(playerData.registrationToken);
                }
            });
            return;
        });
    }

     messageToOthers = {
        data: {
            messageType: 'Accept Matching',
            teamHomeName: teamHome.data().name,
            teamAwayName: teamAway.data().name,
            fieldName: field.data().name, 
            startTime :  timeGame.data().startTime,    
            endTime : timeGame.data().endTime
        },
        tokens: teamMemberRegistrationTokens
    }
  
    }
  
        admin.messaging().sendMulticast(messageToOthers).then((response) => {
            console.log('Successfully sent message: ', response);
            return;
        }).catch((error) => {
            console.log('Error sending message: ', error);
            return;
        })

      
    });  
