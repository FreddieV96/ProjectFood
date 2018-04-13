var admin = require('firebase-admin');

var serviceaccount = require('./config.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceaccount),
    databaseURL: 'https://projectfood-9031e.firebaseio.com'
})

var db = admin.firestore();

var citiesRef = db.collection('cities');
var query = citiesRef.where('capital', '==', true).get()
    .then(snapshot => {
      snapshot.forEach(doc => {
        console.log(doc.id, '=>', doc.data());
      });
    })
    .catch(err => {
      console.log('Error getting documents', err);
    });

