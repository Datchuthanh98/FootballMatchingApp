const functions = require('firebase-functions');
const admin = require('firebase-admin');
const db = admin.firestore();



exports.getListBookingByField = functions.https.onCall(async (idField) => {
    let i;
    let listIDBooking = await db.collection('Field').doc(idField).collection("listBooking").get();
    listBookingPromises = [];
    if (!listIDBooking.empty) {
        for (i = 0; i < listIDBooking.docs.length; i++) {
            listBookingPromises.push(db.collection('Booking').doc(listIDBooking.docs[i].data().booking).get());
        }
    }

    listDataBooking = [];

    await Promise.all(listBookingPromises).then((ListBookingRecords) => {
        for (i = 0; i < ListBookingRecords.length; i++) {
            if(ListBookingRecords[i].data().approve === null){
            listDataBooking.push(ListBookingRecords[i].data())
            }

        }
        return null;
    })


    let listTeamHomePromises = [];
    let listFieldPromises = [];
    let listTeamAwayPromises = [];
    if (!listDataBooking.empty) {
        for (i = 0; i < listDataBooking.length; i++) {
            console.log(listDataBooking[i]);
            listTeamHomePromises.push(db.collection('Team').doc(listDataBooking[i].idTeamHome).get());
            if (!listDataBooking[i].idTeamAway) {
                listTeamAwayPromises.push(null);
            } else {
                listTeamAwayPromises.push(db.collection('Team').doc(listDataBooking[i].idTeamAway).get());
            }

            listFieldPromises.push(db.collection('Field').doc(listDataBooking[i].idField).get());
        
        }

        await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
            for (i = 0; i < ListTeamHomeRecords.length; i++) {
                listDataBooking[i].idTeamHome = ListTeamHomeRecords[i].data();
            }
            return null;
        })


        await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
            for (i = 0; i < ListTeamAwayRecords.length; i++) {
                if (!ListTeamAwayRecords[i]) {
                    listDataBooking.idTeamAway = null;
                } else {
                    listDataBooking[i].idTeamAway = ListTeamAwayRecords[i].data();
                }

            }
            return null;
        })

        await Promise.all(listFieldPromises).then((ListFieldRecords) => {
            for (i = 0; i < ListFieldRecords.length; i++) {
                listDataBooking[i].idField = ListFieldRecords[i].data();
            }
            return null;
        })

    }
    return listDataBooking;
});


exports.getListBookingByTeamm = functions.https.onCall(async (idField) => {
    console.log(idField)
    let i;
    let listIDBooking = await db.collection('Team').doc(idField).collection("listBooking").get();
    listBookingPromises = [];
    if (!listIDBooking.empty) {
        for (i = 0; i < listIDBooking.docs.length; i++) {
            listBookingPromises.push(db.collection('Booking').doc(listIDBooking.docs[i].data().booking).get());
        }
    }

    listDataBooking = [];

    await Promise.all(listBookingPromises).then((ListBookingRecords) => {
        for (i = 0; i < ListBookingRecords.length; i++) {  
            listDataBooking.push(ListBookingRecords[i].data())
        }
        return null;
    })


    let listTeamHomePromises = [];
    let listFieldPromises = [];
    let listTeamAwayPromises = [];
    if (!listDataBooking.empty) {
        for (i = 0; i < listDataBooking.length; i++) {
            console.log(listDataBooking[i]);
            listTeamHomePromises.push(db.collection('Team').doc(listDataBooking[i].idTeamHome).get());
            if (!listDataBooking[i].idTeamAway) {
                listTeamAwayPromises.push(null);
            } else {
                listTeamAwayPromises.push(db.collection('Team').doc(listDataBooking[i].idTeamAway).get());
            }

            listFieldPromises.push(db.collection('Field').doc(listDataBooking[i].idField).get());
        
        }

        await Promise.all(listTeamHomePromises).then((ListTeamHomeRecords) => {
            for (i = 0; i < ListTeamHomeRecords.length; i++) {
                listDataBooking[i].idTeamHome = ListTeamHomeRecords[i].data();
            }
            return null;
        })


        await Promise.all(listTeamAwayPromises).then((ListTeamAwayRecords) => {
            for (i = 0; i < ListTeamAwayRecords.length; i++) {
                if (!ListTeamAwayRecords[i]) {
                    listDataBooking.idTeamAway = null;
                } else {
                    listDataBooking[i].idTeamAway = ListTeamAwayRecords[i].data();
                }

            }
            return null;
        })

        await Promise.all(listFieldPromises).then((ListFieldRecords) => {
            for (i = 0; i < ListFieldRecords.length; i++) {
                listDataBooking[i].idField = ListFieldRecords[i].data();
            }
            return null;
        })

    
    }
    return listDataBooking;
});






exports.createBooking = functions.https.onCall(async (data) => {
    const teamData = {
        idTeamHome: data.idTeamHome,
        idTeamAway: data.idTeamAway,
        idField: data.idField,
        startTime : data.startTime,
        endTime : data.endTime,
        cost : data.cost,
        position : data.position,
        date: data.date,
        note: data.note,
        phone: data.phone,
        time_create:data.time_create,
        approve: null,
    }
    const newTeamRef = await db.collection('Booking').doc();
    teamData.id = newTeamRef.id;
    await newTeamRef.set(teamData);

    const newTeamBooking = await db.collection('Team').doc(data.idTeamHome).collection("listBooking").doc(teamData.id);
    const newFieldBooking = await db.collection('Field').doc(data.idField).collection("listBooking").doc(teamData.id);

    const BookingData = {
        booking: teamData.id
    }

    await newTeamBooking.set(BookingData);
    await newFieldBooking.set(BookingData);

    return {
        result: 1,
        message: 'Team created'
    }

})



