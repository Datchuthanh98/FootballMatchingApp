const functions = require('firebase-functions');
const admin = require('firebase-admin');
const db = admin.firestore();

exports.getListBookingByField = functions.https.onCall(async (idField) => {
    let i;
    let listBookingRecords = await db.collection('Booking').get();
    let listBooking = [];
    let listTeamHomePromises = [];
    let listFieldPromises = [];
    let listTeamAwayPromises = [];
    let listTimePromises = [];
    if (!listBookingRecords.empty) {
        for (i = 0; i < listBookingRecords.docs.length; i++) {
            listBooking[i] = listBookingRecords.docs[i].data();
            listTeamHomePromises.push(db.collection('Team').doc(listBookingRecords.docs[i].data().idTeamHome).get());
            if (listBookingRecords.docs[i].data().idTeamAway !== null){
                listTeamAwayPromises.push(db.collection('Team').doc(listBookingRecords.docs[i].data().idTeamAway).get());
            }else {
                listTeamAwayPromises.push(null);
            }

            listFieldPromises.push(db.collection('Field').doc(listBookingRecords.docs[i].data().idField).get());
            listTimePromises.push(db.collection('TimeGame').doc(listBookingRecords.docs[i].data().idTimeGame).get());
        }

        await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
            for (i = 0; i < ListTeamHomeRecords.length; i++) {
                listBooking[i].idTeamHome = ListTeamHomeRecords[i].data();
            }
            return null;
        })

        await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
            for( i = 0 ; i< ListTeamAwayRecords.length ; i++){
                if (ListTeamAwayRecords[i] !== null){
                    listBooking[i].idTeamAway = ListTeamAwayRecords[i].data();
                }
            }
            return null;
        })

        await Promise.all(listFieldPromises).then((ListFieldRecords) => {
            for (i = 0; i < ListFieldRecords.length; i++) {
                listBooking[i].idField = ListFieldRecords[i].data();
            }
            return null;
        })

        await Promise.all(listTimePromises).then((ListTimeRecords) => {
            for (i = 0; i < ListTimeRecords.length; i++) {
                listBooking[i].idTimeGame = ListTimeRecords[i].data();
            }
            return null;
        })


        listMyBooking = [];

        //filterBy idPlayer
        for (i = 0; i < listBooking.length; i++) {
            if (listBooking[i].idField.id === idField && listBooking[i].approve === null) {
                listMyBooking.push(listBooking[i])
            }
        }
    }
    return listMyBooking;
});




