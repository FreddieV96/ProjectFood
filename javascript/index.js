var admin = require('firebase-admin');
var CSVReader = require('./lib/CSVToJSON')
var path = 'C:\\Users\\tyk1_\\Downloads\\opskrifter.csv';

var serviceaccount = require('./config.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceaccount),
    databaseURL: 'https://projectfood-9031e.firebaseio.com'
})

var db = admin.firestore();

CSVReader(path, function(res) {

  var recRef = db.collection('recipes');


  res.forEach(rec => {
    if(rec.id != '') {
      rec.mængde = rec.mængde.split("#")
      rec.ingredientsTemp = rec.ingredients.split('#')
      rec.ingredients = [];
      for(var i = 0; i < rec.mængde.length; i++) {
        var ingredient = {};
        ingredient.title = rec.ingredientsTemp[i];
        ingredient.amount = rec.mængde[i]
        rec.ingredients.push(ingredient);
      }
      delete rec.ingredientsTemp;
      delete rec.mængde;
    }

    /*var doc = db.doc(rec.id);
    delete recRef.id
    var setRec = doc.set(rec)*/
  });
  
  res = res.filter(c => c.id != '')
  console.log(res)
  
  res.forEach(rec => {
    if(rec.id != '') {
      var docRef = recRef.doc(rec.id);
      delete rec.id;
      docRef.set(rec);
    }
  })


})


/*var citiesRef = db.collection('cities');
var query = citiesRef.where('capital', '==', true).get()
    .then(snapshot => {
      snapshot.forEach(doc => {
        console.log(doc.id, '=>', doc.data());
      });
    })
    .catch(err => {
      console.log('Error getting documents', err);
    });*/

