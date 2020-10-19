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
        introduce: null,
        level: null,
        rating : 10 ,
        reputaion : 1,
        idCaptain : data.idPlayer
    }

    const newTeamRef = await db.collection('Team').doc();
    teamData.id = newTeamRef.id;
    await newTeamRef.set(teamData);

    const newTeamMemberRef = await db.collection('TeamMember').doc();

    const teamMemberData = {
        id : newTeamMemberRef.id,
        idPlayer : data.idPlayer,
        idTeam : newTeamRef.id
    }

    await newTeamMemberRef.set(teamMemberData).catch((error) => {
        return {
            result: 0,
            message: 'Add teammember into database failed',
            errorCode: error.code,
            errorMessage: error.message
        }
    });
    return {
        result: 1,
        message: 'Team created'
    }

})

exports.getTeamDetail = functions.https.onCall(async (uid) => {
    let i;
    const teamRef = db.collection('Team').doc(uid);
    const teamRecord = await teamRef.get();
    if(teamRecord.exists){
        let result  = teamRecord.data();
        let players = [];
        const teamMembers = await db.collection('TeamMember').where('idTeam','==',teamRecord.data().id).get();
        if(!teamMembers.empty){
            let playerPromises = [];
            for(i =0 ; i < teamMembers.docs.length ; i++){
                playerPromises.push(db.collection('Team').doc(teamMembers.docs[i].data().idTeam).get());
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
    const members = await db.collection('TeamMember').where('idPlayer','==',idPlayer).get();
    if(!members.empty){
        let listTeam = [] ;
        let listTeamPromises = [];
        for(i =0 ; i < members.docs.length ; i++){
            listTeamPromises.push(db.collection('Team').doc(members.docs[i].data().idTeam).get());
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
    let i ;
    const listTeam = [];
    const members = await db.collection('TeamMember').where('idPlayer','==',idPlayer).get();
      const allTeam = await db.collection('Team').get();

      allTeam.docs.forEach(team => {
        for( i = 0 ;  i<members.docs.length ; i ++){
            if(members.docs[i].data().idTeam === team.data().id){
               break;
            }else if(i === members.docs.length){
                listTeam.push(team.data())
            }
        }

      })
    
      return allTeam.docs;
 
})


