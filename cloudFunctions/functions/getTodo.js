const functions = require('firebase-functions');

const admin = require('firebase-admin');
const db = admin.firestore();

exports.getTodo = functions.https.onCall(async (id)=>{
    const record = await db.collection('Todo').doc(id).get();
    const data = record.data();
    return data;
});

exports.getTodo2 = functions.https.onCall(async (request)=>{
    const id = request.id;
    console.log("id "+id);
    const record = await db.collection('Todo').doc(id).get();
    const data = record.data();
    return data.name;
});