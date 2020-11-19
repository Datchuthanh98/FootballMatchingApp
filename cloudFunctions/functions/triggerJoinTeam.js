const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();
const auth = admin.auth();

// thông bao đến sân , có người muốn vào đội
exports.triggerJoinTeam = functions.firestore.document('Team/{idTeam}/listRequest/{requestId}').onCreate(async (snap, context) => {
    const newValue = snap.data();
    console.log(context.params.requestId);
    // get team
    // const idTeam = newValue.idTeam;
    const idTeam = context.params.idTeam;
    const teamRecord = await db.collection('Team').doc(idTeam).get();
    const teamData = teamRecord.data();
    // get player
    const idPlayer = newValue.player;
    const playerRecord = await db.collection('Player').doc(idPlayer).get();
    const playerData = playerRecord.data();
    // get captain
    const idCaptain = teamData.idCaptain;
    const captainRecord = await db.collection('Player').doc(idCaptain).get();
    const captainData = captainRecord.data();
    // get captain's registration token
    const captainToken = captainData.registrationToken;
    const message = {
        data: {
            messageType: "NewJoinRequest",
            playerId: idPlayer,
            playerName: playerData.name
        },
        token: captainToken
    }
    admin.messaging().send(message).then((response) => {
        console.log('Successfully sent message: ', response);
        return;
    }).catch((error) => {
        console.log('Error sending message: ', error);
        return;
    })
});


//thông báo tới các thành viên của đội là có người gia nhập tên x,  thông báo tới cầu thủ x là đã đc gia nhập đội, tự xem lại logic
exports.triggerJoinTeamToAllMembers = functions.firestore.document('Team/{idTeam}/listPlayer/{recordId}').onCreate(async (snap, context) => {
    const newValue = snap.data();
    // get new member data
    const idNewMember = newValue.player;
    const newMemberRecord = await db.collection('Player').doc(idNewMember).get();
    const newMemberData = newMemberRecord.data();
    const newMemberRegistrationToken = newMemberData.registrationToken;
    // get team data
    // const teamId = newValue.idTeam;
    const teamId = context.params.idTeam;
    const teamRecord = await db.collection('Team').doc(teamId).get();
    const teamData = teamRecord.data();
    // check if the member is leader
    if (teamData.idCaptain === idNewMember){
        // do nothing
    } else {
        // Send welcome message to new member
        if (newMemberRecord.get('registrationToken') !== null){
            const messageToNewMember = {
                data: {
                    messageType: 'Joined Team',
                    teamId: teamId,
                    teamName: teamData.name
                },
                token: newMemberRegistrationToken
            }
            admin.messaging().send(messageToNewMember).then((response) => {
                console.log('Successfully sent message: ', response);
                return;
            }).catch((error) => {
                console.log('Error sending message: ', error);
                return;
            })
        }

        // Send message to other team members
        // get other team members
        const teamMemberRegistrationTokens = [];
        // teamMemberRegistrationTokens.push(newMemberRegistrationToken);
        // const teamMemberRecords = await db.collection('TeamMember').where('idTeam', '==', teamId).get();
        const teamMemberRecords = await db.collection('Team').doc(teamId).collection('listPlayer').get();
        if (!teamMemberRecords.empty) {
            const teamMembersPromises = [];
            let i;
            for (i = 0 ; i < teamMemberRecords.docs.length; i++){
                const teamMemberData = teamMemberRecords.docs[i].data();
                const idPlayer = teamMemberData.player;
                if (idPlayer !== idNewMember){
                    teamMembersPromises.push(db.collection('Player').doc(idPlayer).get());
                }
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

        const messageToOthers = {
            data: {
                messageType: 'New Member',
                teamId: teamId,
                teamName: teamData.name,
                memberId: idNewMember,
                memberName: newMemberData.name
            },
            tokens: teamMemberRegistrationTokens
        }
        admin.messaging().sendMulticast(messageToOthers).then((response) => {
            console.log('Successfully sent message: ', response);
            return;
        }).catch((error) => {
            console.log('Error sending message: ', error);
            return;
        })
    }
    
})