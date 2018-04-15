var admin = require('firebase-admin');
var CSVReader = require('./lib/CSVToJSON')

var serviceAccount = require('./config.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://projectfood-4a086.firebaseio.com'
});

var db = admin.firestore();

//db.collection('recipes').doc('1').set({test: "test"})

CSVReader('./lib/opskrifter.csv', (res) => {
  res = res.filter(rec => rec.id != '')
  res.forEach(rec => {
    var id = rec.id
    rec.ingredients = rec.ingredients.split("# ")
    rec.temp = []
    rec.mængde = rec.mængde.split("# ")
    for(var i = 0; i < rec.mængde.length; i++) {
      var ingredient = {
        title : rec.ingredients[i],
        amount : rec.mængde[i]
      }
      rec.temp.push(ingredient)
    }
    rec.ingredients = rec.temp
    delete rec.temp
    delete rec.mængde

    delete rec.id
    rec.picturePath = '/picture/' + id + ".jpg"

    //console.log(rec)

    db.collection('recipes').doc(id).set(rec)
  });
})