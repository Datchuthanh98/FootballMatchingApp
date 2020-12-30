const functions = require('firebase-functions');
const admin = require('firebase-admin');
const db = admin.firestore();


exports.getListMatchByDate = functions.https.onCall(async (date) => {
    const startDate = Number(date.startDate);
    const endDate = Number(date.endDate);
    let i;
    let listMatchRecords = await db.collection('Match').where('date','<=',endDate).where('date','>=',startDate).get();
        const listMatch = [];
        let listTeamHomePromises = [];
        let listFieldPromises = [];
        let listTeamAwayPromises = [];
     
        if(!listMatchRecords.empty){
            let listBookingPromises = [];
            for(i = 0 ; i < listMatchRecords.docs.length ; i++){
                listMatch.push(listMatchRecords.docs[i].data());
                listBookingPromises.push(db.collection('Booking').doc(listMatchRecords.docs[i].data().idBooking).get());
            }

            await Promise.all(listBookingPromises).then((BookingRecords) => {
                for( i = 0 ; i< BookingRecords.length ; i++){
                    if (BookingRecords[i].exists){
                            listMatch[i].idBooking = BookingRecords[i].data(); 
                            listTeamHomePromises.push(db.collection('Team').doc(BookingRecords[i].data().idTeamHome).get());
                            if (BookingRecords[i].data().idTeamAway !== null){
                                listTeamAwayPromises.push(db.collection('Team').doc(BookingRecords[i].data().idTeamAway).get());
                            }
                            else {
                                listTeamAwayPromises.push(null);
                            }
                            listFieldPromises.push(db.collection('Field').doc(BookingRecords[i].data().idField).get());
                         
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

        
        }  
    return listMatch; 
});


exports.getListMatchByField = functions.https.onCall(async (idField) => {
    let i;
    let listMatchID = await db.collection('Field').doc(idField).collection("listMatch").get();
  
    //get data match basic
    listMatchPromises = [];
    if (!listMatchID.empty) {
        for (i = 0; i < listMatchID.docs.length; i++) {
            listMatchPromises.push(db.collection('Match').doc(listMatchID.docs[i].data().booking).get());
        }
    } 


    listDataMatch = [];

    await Promise.all(listMatchPromises).then((ListMatchRecords) => {
        for (i = 0; i < ListMatchRecords.length; i++) {
            console.log(ListMatchRecords[i].data())
            listDataMatch.push(ListMatchRecords[i].data())
        }
        return null;
    })


     
    //get data booking basic
    listBookingPromises = [];
    if (!listDataMatch.empty) {
        for (i = 0; i < listDataMatch.length; i++) {
            listBookingPromises.push(db.collection('Booking').doc(listDataMatch[i].idBooking).get());
        }
    } 

    listDataBooking = [];

    await Promise.all(listBookingPromises).then((ListBookingRecords) => {
        for (i = 0; i < ListBookingRecords.length; i++) {  
            console.log(ListBookingRecords[i].data());
            listDataMatch[i].idBooking = ListBookingRecords[i].data();
        }
        return null;
    })

    
     //get data booking detail
    let listTeamHomePromises = [];
    let listFieldPromises = [];
    let listTeamAwayPromises = [];

    if (!listDataMatch.empty) {
        for (i = 0; i < listDataMatch.length; i++) {
            listTeamHomePromises.push(db.collection('Team').doc(listDataMatch[i].idBooking.idTeamHome).get());
            if (!listDataMatch[i].idBooking.idTeamAway) {
                listTeamAwayPromises.push(null);
            } else {
                listTeamAwayPromises.push(db.collection('Team').doc(listDataMatch[i].idBooking.idTeamAway).get());
            }
            listFieldPromises.push(db.collection('Field').doc(listDataMatch[i].idBooking.idField).get());
        
        }

        await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
            for (i = 0; i < ListTeamHomeRecords.length; i++) {
                listDataMatch[i].idBooking.idTeamHome = ListTeamHomeRecords[i].data();
            }
            return null;
        })


        await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
   
            for (i = 0; i < ListTeamAwayRecords.length; i++) {
                if (!ListTeamAwayRecords[i]) {
                    listDataMatch[i].idBooking.idTeamAway = null;
                } else {
                    listDataMatch[i].idBooking.idTeamAway = ListTeamAwayRecords[i].data();
                }

            }
            return null;
        })

        await Promise.all(listFieldPromises).then((ListFieldRecords) => {
            for (i = 0; i < ListFieldRecords.length; i++) {
                listDataMatch[i].idBooking.idField = ListFieldRecords[i].data();
            }
            return null;
        })

      
    }
    console.log(listDataMatch)
    return listDataMatch;

})


exports.getListMatchByTeam = functions.https.onCall(async (idTeam) => {
    let i;
    let listMatchID = await db.collection('Team').doc(idTeam).collection("listMatch").get();
  
    //get data match basic
    listMatchPromises = [];
    if (!listMatchID.empty) {
        for (i = 0; i < listMatchID.docs.length; i++) {
            listMatchPromises.push(db.collection('Match').doc(listMatchID.docs[i].data().booking).get());
        }
    } 


    listDataMatch = [];

    await Promise.all(listMatchPromises).then((ListMatchRecords) => {
        for (i = 0; i < ListMatchRecords.length; i++) {
            listDataMatch.push(ListMatchRecords[i].data())
        }
        return null;
    })


     
    //get data booking basic
    listBookingPromises = [];
    if (!listDataMatch.empty) {
        for (i = 0; i < listDataMatch.length; i++) {
            listBookingPromises.push(db.collection('Booking').doc(listDataMatch[i].idBooking).get());
        }
    } 

    listDataBooking = [];

    await Promise.all(listBookingPromises).then((ListBookingRecords) => {
        for (i = 0; i < ListBookingRecords.length; i++) {  
            console.log(ListBookingRecords[i].data());
            listDataMatch[i].idBooking = ListBookingRecords[i].data();
        }
        return null;
    })

    
     //get data booking detail
    let listTeamHomePromises = [];
    let listFieldPromises = [];
    let listTeamAwayPromises = [];

    if (!listDataMatch.empty) {
        for (i = 0; i < listDataMatch.length; i++) {
            listTeamHomePromises.push(db.collection('Team').doc(listDataMatch[i].idBooking.idTeamHome).get());
            if (!listDataMatch[i].idBooking.idTeamAway) {
                listTeamAwayPromises.push(null);
            } else {
                listTeamAwayPromises.push(db.collection('Team').doc(listDataMatch[i].idBooking.idTeamAway).get());
            }
            listFieldPromises.push(db.collection('Field').doc(listDataMatch[i].idBooking.idField).get());
           
        }

        await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
            for (i = 0; i < ListTeamHomeRecords.length; i++) {
                listDataMatch[i].idBooking.idTeamHome = ListTeamHomeRecords[i].data();
            }
            return null;
        })


        await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
   
            for (i = 0; i < ListTeamAwayRecords.length; i++) {
                if (!ListTeamAwayRecords[i]) {
                    listDataMatch[i].idBooking.idTeamAway = null;
                } else {
                    listDataMatch[i].idBooking.idTeamAway = ListTeamAwayRecords[i].data();
                }

            }
            return null;
        })

        await Promise.all(listFieldPromises).then((ListFieldRecords) => {
            for (i = 0; i < ListFieldRecords.length; i++) {
                listDataMatch[i].idBooking.idField = ListFieldRecords[i].data();
            }
            return null;
        })
    }
    return listDataMatch;

})





exports.getMatchDetail = functions.https.onCall(async (idMatch) => {
    let matchRecord = await db.collection('Match').doc(idMatch).get();
    let match =  matchRecord.data();
    let bookingRecord = await db.collection("Booking").doc(matchRecord.data().idBooking).get();
    match.idBooking = bookingRecord.data();
    let fieldRecord = await db.collection("Field").doc(bookingRecord.data().idField).get();
    match.idBooking.idField = fieldRecord.data();
    let teamHomeRecord = await db.collection("Team").doc(bookingRecord.data().idTeamHome).get();
    match.idBooking.idTeamHome =teamHomeRecord.data();
    if(bookingRecord.data().idTeamAway !== null){
        let teamAwayRecord = await db.collection("Team").doc(bookingRecord.data().idTeamAway).get();
        match.idBooking.idTeamAway =teamAwayRecord.data();
    }
  
    return match;
});

exports.getListQueueTeam = functions.https.onCall(async (idMatch) => {
    const listQueueTeamRecord = await db.collection("Match").doc(idMatch).collection("listQueueTeam").orderBy("time_create",'desc').get();
 
    let i ;
    let listTeamPromises = []
    for( i =0 ; i<listQueueTeamRecord.docs.length ; i++){
        listTeamPromises.push(db.collection("Team").doc(listQueueTeamRecord.docs[i].data().team).get());
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
    const listCommentRecord = await db.collection("Match").doc(idMatch).collection("listComment").orderBy("time_create",'desc').get();
    let listComment = [];
    let i ;
    let listPlayerPromises = []
    for( i =0 ; i<listCommentRecord.docs.length ; i++){
        listComment.push(listCommentRecord.docs[i].data());
        listPlayerPromises.push(db.collection("Player").doc(listCommentRecord.docs[i].data().player).get());
    }

    await Promise.all(listPlayerPromises).then((playerRecords) => {
        for( i = 0 ; i< playerRecords.length ; i++){
            listComment[i].idPlayer = playerRecords[i].data();
         }
         return null;

    })
    return listComment;
});



exports.createMatch = functions.https.onCall(async (data) => {
    const teamData = {
        idBooking: data.idBooking,
        scoreHome: null,
        scoreAway: null,
        active : false,
        date: Number(data.date)
    }
    const newTeamRef = await db.collection('Match').doc();
    teamData.id = newTeamRef.id;
    await newTeamRef.set(teamData);

    const newTeamBooking = await db.collection('Team').doc(data.idTeamHome).collection("listMatch").doc(teamData.id);
    const newFieldBooking = await db.collection('Field').doc(data.idField).collection("listMatch").doc(teamData.id);


    const BookingData = {
        booking : teamData.id
    }

    await newTeamBooking.set(BookingData);
    await newFieldBooking.set(BookingData);
    await db.collection('Booking').doc(data.idBooking).update({approve:true});

    return {
        result: 1,
        message: 'Team created'
    }

})
