const functions = require('firebase-functions');
const admin = require('firebase-admin');
const db = admin.firestore();

exports.getAllListMatch = functions.https.onCall(async () => {
    let i;
        let listMatchRecords = await db.collection('Match').get();
        const listMatch = [];
        let listTeamHomePromises = [];
        let listFieldPromises = [];
        let listTeamAwayPromises = [];
        let listTimePromises = [];
        if(!listMatchRecords.empty){
            let listBookingPromises = [];
            for(i = 0 ; i < listMatchRecords.docs.length ; i++){
                listMatch.push(listMatchRecords.docs[i].data());
                listBookingPromises.push(db.collection('Booking').doc(listMatchRecords.docs[i].data().idBooking).get());
            }

            await Promise.all(listBookingPromises).then((BookingRecords) => {
                for( i = 0 ; i< BookingRecords.length ; i++){
                    if (BookingRecords[i].exists){
                        if(BookingRecords[i].data().approve === true){
                            listMatch[i].idBooking = BookingRecords[i].data(); 
                            listTeamHomePromises.push(db.collection('Team').doc(BookingRecords[i].data().idTeamHome).get());
                            if (BookingRecords[i].data().idTeamAway !== null){
                                listTeamAwayPromises.push(db.collection('Team').doc(BookingRecords[i].data().idTeamAway).get());
                            }
                            else {
                                listTeamAwayPromises.push(null);
                            }
                            listFieldPromises.push(db.collection('Field').doc(BookingRecords[i].data().idField).get());
                            listTimePromises.push(db.collection('TimeGame').doc(BookingRecords[i].data().idTimeGame).get());
                        }
                    }
                }
                return null;
            });

            await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
                for( i = 0 ; i< ListTeamHomeRecords.length ; i++){
                   listMatch[i].idBooking.idTeamHome = ListTeamHomeRecords[i].data();
                }
                return null;
            })

            await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
                for( i = 0 ; i< ListTeamAwayRecords.length ; i++){
                    if (ListTeamAwayRecords[i]!==null){
                        listMatch[i].idBooking.idTeamAway = ListTeamAwayRecords[i].data();
                    }
                   
                }
                return null;
            })

            await Promise.all(listFieldPromises).then((ListFieldRecords) => {
                for( i = 0 ; i< ListFieldRecords.length ; i++){
                   listMatch[i].idBooking.idField = ListFieldRecords[i].data();
                }
                return null;
            })

            await Promise.all(listTimePromises).then((ListTimeRecords) => {
                for( i = 0 ; i< ListTimeRecords.length ; i++){
                   listMatch[i].idBooking.idTimeGame = ListTimeRecords[i].data();
                }
                return null;
            })
        }  
    return listMatch; 
});


exports.getMyListMatch = functions.https.onCall(async (idPlayer) => {
    let i,j;
        let listMatchRecords = await db.collection('Match').get();
        let members = await db.collection('TeamMember').where('idPlayer','==',idPlayer).get();
        const listMatch = [];
        let listTeamHomePromises = [];
        let listFieldPromises = [];
        let listTeamAwayPromises = [];
        let listTimePromises = [];
        if(!listMatchRecords.empty){
            let listBookingPromises = [];
            for(i = 0 ; i < listMatchRecords.docs.length ; i++){
                listMatch.push(listMatchRecords.docs[i].data());
                listBookingPromises.push(db.collection('Booking').doc(listMatchRecords.docs[i].data().idBooking).get());
            }

            await Promise.all(listBookingPromises).then((BookingRecords) => {
                for( i = 0 ; i< BookingRecords.length ; i++){
                    if (BookingRecords[i].exists){
                        if(BookingRecords[i].data().approve === true){
                            listMatch[i].idBooking = BookingRecords[i].data(); 
                            listTeamHomePromises.push(db.collection('Team').doc(BookingRecords[i].data().idTeamHome).get());
                            if (BookingRecords[i].data().idTeamAway !== null){
                                listTeamAwayPromises.push(db.collection('Team').doc(BookingRecords[i].data().idTeamAway).get());
                            }
                            else {
                                listTeamAwayPromises.push(null);
                            }
                            listFieldPromises.push(db.collection('Field').doc(BookingRecords[i].data().idField).get());
                            listTimePromises.push(db.collection('TimeGame').doc(BookingRecords[i].data().idTimeGame).get());
                        }
                    }
                    
                }
                return null;
            });

            await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
                for( i = 0 ; i< ListTeamHomeRecords.length ; i++){
                   listMatch[i].idBooking.idTeamHome = ListTeamHomeRecords[i].data();
                }
                return null;
            })


            
            await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
                for( i = 0 ; i< ListTeamAwayRecords.length ; i++){
                    if (ListTeamAwayRecords[i] !== null){
                        listMatch[i].idBooking.idTeamAway = ListTeamAwayRecords[i].data();
                    }
                }
                return null;
            })

            await Promise.all(listFieldPromises).then((ListFieldRecords) => {
                for( i = 0 ; i< ListFieldRecords.length ; i++){
                   listMatch[i].idBooking.idField = ListFieldRecords[i].data();
                }
                return null;
            })

            await Promise.all(listTimePromises).then((ListTimeRecords) => {
                for( i = 0 ; i< ListTimeRecords.length ; i++){
                   listMatch[i].idBooking.idTimeGame = ListTimeRecords[i].data();
                }
                return null;
            })
 

            listMyTeam = [];

            //filterBy idPlayer
            for(i =0 ;i < listMatch.length ; i++){
                for( j = 0 ;  j<members.docs.length ; j ++){
                    if(members.docs[j].data().idTeam === listMatch[i].idBooking.idTeamHome.id ||  members.docs[j].data().idTeam === listMatch[i].idBooking.idTeamAway.id){
                        listMyTeam.push(listMatch[i])
                    }else if(j === (members.docs.length-1)){
                       //do somthing
                    }
                }
              }

        }  
    return listMyTeam; 
});


exports.getMatchDetail = functions.https.onCall(async (idMatch) => {
    let matchRecord = await db.collection('Match').doc(idMatch).get();
    let match =  matchRecord.data();
    let bookingRecord = await db.collection("Booking").doc(matchRecord.data().idBooking).get();
    match.idBooking = bookingRecord.data();
    let fieldRecord = await db.collection("Field").doc(bookingRecord.data().idField).get();
    match.idBooking.idField = fieldRecord.data();
    let timeRecord = await db.collection("TimeGame").doc(bookingRecord.data().idTimeGame).get();
    match.idBooking.idTimeGame = timeRecord.data();
    let teamHomeRecord = await db.collection("Team").doc(bookingRecord.data().idTeamHome).get();
    match.idBooking.idTeamHome =teamHomeRecord.data();
    if(bookingRecord.data().idTeamAway !== null){
        let teamAwayRecord = await db.collection("Team").doc(bookingRecord.data().idTeamAway).get();
        match.idBooking.idTeamAway =teamAwayRecord.data();
    }
   
    const listQueueTeamRecord = await db.collection("QueueTeam").where('idMatch','==',idMatch).get();
 
    let i ;
    let listTeamPromises = []
    for( i =0 ; i<listQueueTeamRecord.docs.length ; i++){
        listTeamPromises.push(db.collection("Team").doc(listQueueTeamRecord.docs[i].data().idTeam).get());
    }

    let listTeam= []

    await Promise.all(listTeamPromises).then((teamRecords) => {
        for( i = 0 ; i< teamRecords.length ; i++){
            listTeam.push(teamRecords[i].data());
         }
         return null;

    })
    match.listQueueTeam = listTeam;   
    return match;
});

exports.getListQueueTeam = functions.https.onCall(async (idMatch) => {
    const listQueueTeamRecord = await db.collection("QueueTeam").where('idMatch','==',idMatch).get();
 
    let i ;
    let listTeamPromises = []
    for( i =0 ; i<listQueueTeamRecord.docs.length ; i++){
        listTeamPromises.push(db.collection("Team").doc(listQueueTeamRecord.docs[i].data().idTeam).get());
    }

    let listTeam= [];

    await Promise.all(listTeamPromises).then((teamRecords) => {
        for( i = 0 ; i< teamRecords.length ; i++){
            listTeam.push(teamRecords[i].data());
         }
         return null;

    })
    return listTeam;
});




exports.getCommentMatch = functions.https.onCall(async (idMatch) => {    
    const listCommentRecord = await db.collection("Comment").where('idMatch','==',idMatch).get();
    let listComment = [];
    let i ;
    let listPlayerPromises = []
    for( i =0 ; i<listCommentRecord.docs.length ; i++){
        listComment.push(listCommentRecord.docs[i].data());
        listPlayerPromises.push(db.collection("Player").doc(listCommentRecord.docs[i].data().idPlayer).get());
    }

    await Promise.all(listPlayerPromises).then((playerRecords) => {
        for( i = 0 ; i< playerRecords.length ; i++){
            listComment[i].idPlayer = playerRecords[i].data();
         }
         return null;

    })
    return listComment;
});


