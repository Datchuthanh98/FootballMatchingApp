const functions = require('firebase-functions');
const admin = require('firebase-admin');
const db = admin.firestore();

exports.createUser = functions.https.onCall(async (data) => {
    try {
        const userRecord = await admin.auth().createUser({
            email: data.email,
            password: data.password,
            displayName: data.name
        });

        const userInfo = {
            id:userRecord.uid,
            email: data.email,
            name: data.name,
            urlAvatar: "/Avatar/default",
            urlCover: "/Cover/default"
            // height: 0,
            // weight: 0,
            // address: null,
            // birthday: null,
            // phone: null,
            // accountActive: true,
            // introduce: null,
            // position :null,
            // level :null,
            // foot : null
        };
        await db.collection('Player').doc(userRecord.uid).set(userInfo).catch((error) => {
            return {
                result: 0,
                message: 'Add player into database failed',
                errorCode: error.code,
                errorMessage: error.message
            }
        });
        return {
            result : 1,
            data: userInfo,
            message: 'User created'
        }
    }
    catch (error) {
        return {
            result: 0,
            message: 'Create user failed',
            errorCode: error.code,
            errorMessage: error.message
        }
    }
})

exports.getPlayerDetail = functions.https.onCall(async (uid) => {
    let i;
    const playerRef = db.collection('Player').doc(uid);
    const playerRecord = await playerRef.get();
    if(playerRecord.exists){
        let result  = playerRecord.data();
        let teams = [];
        const teamMembers = await db.collection('TeamMember').where('idPlayer','==',playerRecord.data().id).get();
        if(!teamMembers.empty){
            let teamPromises = [];
            for(i =0 ; i < teamMembers.docs.length ; i++){
                teamPromises.push(db.collection('Team').doc(teamMembers.docs[i].data().idTeam).get());
            }
            await Promise.all(teamPromises).then((teamRecords) => {
                for( i = 0 ; i< teamRecords.length ; i++){
                    teams.push(teamRecords[i].data());
                }
                return null;
            });
            result.teams=teams;
        }  
    return result;

    }else {
        return null;
    }
})




exports.getListPlayer = functions.https.onCall(async (idTeam) => {
    const members = await db.collection('TeamMember').where('idTeam','==',idTeam).get();
    if(!members.empty){
        let listPlayer = [] ;
        let listPlayerPromises = [];
        for(i =0 ; i < members.docs.length ; i++){
            listPlayerPromises.push(db.collection('Player').doc(members.docs[i].data().idPlayer).get());
        }
        await Promise.all(listPlayerPromises).then((playerRecords) => {
            for( i = 0 ; i< playerRecords.length ; i++){
                listPlayer.push(playerRecords[i].data());
            }
            return null;
        });  
      return listPlayer;
    }else{
        return null;
    }

})


exports.getListPlayerRequest = functions.https.onCall(async (idTeam) =>{
    const listRequestJoin = await db.collection('RequestJoinTeam').where('idTeam','==',idTeam).get();
    if(!listRequestJoin.empty){
        let listPlayer = [] ;
        let listPlayerPromises = [];
        for(i =0 ; i < listRequestJoin.docs.length ; i++){
            listPlayerPromises.push(db.collection('Player').doc(listRequestJoin.docs[i].data().idPlayer).get());
        }
        await Promise.all(listPlayerPromises).then((playerRecords) => {
            for( i = 0 ; i< playerRecords.length ; i++){
                listPlayer.push(playerRecords[i].data());
            }
            return null;
        });  
      return listPlayer;
    }else{
        return null;
    }
})

