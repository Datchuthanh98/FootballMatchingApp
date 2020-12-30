const functions = require('firebase-functions');
const admin = require('firebase-admin');
const { error } = require('firebase-functions/lib/logger');
const db = admin.firestore();

exports.acceptJoinTeam = functions.https.onCall(async (data) => {
    
    const idTeam=data.idTeam;
    const idPlayer = data.idPlayer;
    const time_create = data.time_create;

    await db.collection("Team").doc(idTeam).collection("listPlayer").doc(idPlayer).set({player:idPlayer,time_join :time_create});
    await db.collection("Team").doc(idTeam).collection("listRequest").doc(idPlayer).delete();
    await db.collection("Player").doc(idPlayer).collection("listTeam").doc(idTeam).set({team:idTeam,time_join :time_create});
    return {
        result: 1,
        message: 'Team created'
    }

})
