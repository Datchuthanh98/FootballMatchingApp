const functions = require('firebase-functions');
const admin = require('firebase-admin');
const { error } = require('firebase-functions/lib/logger');
const db = admin.firestore();

exports.createTeam = functions.https.onCall(async (data) => {
    const teamData = {
        name: data.name,
        urlAvatar: "/Avatar/default",
        urlCover: "/Cover/default",
        email: data.email,
        phone: data.phone,
        rating : 10 ,
        reputaion : 1,
        idCaptain : data.idPlayer,
        // introduce: null,
        // level: null
    }

    const newTeamRef = await db.collection('Team').doc();
    teamData.id = newTeamRef.id;
    await newTeamRef.set(teamData);

    const newTeamMemberRef = await db.collection('Team').doc(newTeamRef.id).collection("listPlayer").doc(data.idPlayer);
    const teamMemberData = {
        player: data.idPlayer
    }

    const myTeamRef = await db.collection('Player').doc(data.idPlayer).collection("listTeam").doc(teamData.id);
    const myTeamData = {
        team: teamData.id
    }


    await myTeamRef.set(myTeamData);
    await newTeamMemberRef.set(teamMemberData);


    return {
        result: 1,
        message: 'Team created'
    }

})

exports.getTeamDetail = functions.https.onCall(async (idTeam) => {
    let i;
    const teamRef = db.collection('Team').doc(idTeam);
    const teamRecord = await teamRef.get();
    if(teamRecord.exists){
        let result  = teamRecord.data();
        let players = [];
        const teamMembers = await db.collection('Team').doc(idTeam).collection("listPlayer").get();
        if(!teamMembers.empty){
            let playerPromises = [];
            for(i =0 ; i < teamMembers.docs.length ; i++){
                playerPromises.push(db.collection('Player').doc(teamMembers.docs[i].data().player).get());
            }
            await Promise.all(playerPromises).then((playerRecords) => {
                for( i = 0 ; i< playerRecords.length ; i++){
                    players.push(playerRecords[i].data());
                }
                return null;
            });
            result.players=players;
        }   
    return result;

    }else {
        return null;
    }
})


exports.getListTeam = functions.https.onCall(async (idPlayer) => {
    const teams = await db.collection('Player').doc(idPlayer).collection('listTeam').get();
    if(!teams.empty){
        let listTeam = [] ;
        let listTeamPromises = [];
        for(i =0 ; i < teams.docs.length ; i++){
            listTeamPromises.push(db.collection('Team').doc(teams.docs[i].data().team).get());
        }
        await Promise.all(listTeamPromises).then((teamRecords) => {
            for( i = 0 ; i< teamRecords.length ; i++){
                listTeam.push(teamRecords[i].data());
            }
            return null;
        });  
      return listTeam;
    }else{
        return null;
    }

})

exports.getListTeamOther = functions.https.onCall(async (idPlayer) => {
    let i,j ;
    let listTeam = [];
    const listMyTeam = await db.collection('Player').doc(idPlayer).collection('listTeam').get();
    const allTeam = await db.collection('Team').get();
      for(i =0 ;i < allTeam.docs.length ; i++){
        for( j = 0 ;  j<listMyTeam.docs.length ; j ++){
            if(listMyTeam.docs[j].data().team === allTeam.docs[i].data().id){
               break;
            }else if(j === (listMyTeam.docs.length-1)){
                listTeam.push(allTeam.docs[i].data())
            }
        }
      }

     
      return listTeam;
})


exports.getListEvaluate = functions.https.onCall(async (idTeam) => {    
    const listEvaluateRecord = await db.collection('Team').doc(idTeam).collection('listEvaluate').get();
    let listComment = [];
    let i ;
    let listPlayerPromises = []
    for( i =0 ; i<listEvaluateRecord.docs.length ; i++){
        listComment.push(listEvaluateRecord.docs[i].data());
        listPlayerPromises.push(db.collection("Player").doc(listEvaluateRecord.docs[i].data().player).get());
    }

    await Promise.all(listPlayerPromises).then((playerRecords) => {
        for( i = 0 ; i< playerRecords.length ; i++){
            listComment[i].idPlayer = playerRecords[i].data();
         }
         return null;
    }) 
    return listComment;
});

