var admin = require('firebase-admin');
var CSVReader = require('./lib/CSVToJSON')
var path = 'C:\\Users\\tyk1_\\Downloads\\opskrifter.csv';

var serviceaccount = require('./config.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceaccount),
    databaseURL: 'https://projectfood-9031e.firebaseio.com'
})

var db = admin.firestore();

var string = "smør# kyllingefilet# alfredo pasta sovs# broccolibuketter# fettuccine pasta # oksekød# løg# champignon# hvidløg# dåse hakkede tomater# oregano# basilikum# salt# peber# gulerødder# mælk# mornaysauce# lasagneplader# olie# margarine# paprika# lasagne plader# revet ost# bechamelsauce# spinat# hytteost# champignoner# squash# tomater# citronsaft# dåse tomatpure# grøntsagsbouillon# timian# laurbærblade# muskat# chilier# jomfruolivenolie# hakket oksekød# spidskommen# koriander# tomatpure# oksebouillon# røde kidneybønner# mørk chokolade# salt & peber# græsk yoghurt# vand# grødris# sødmælk# sukker# kanel# kyllingebryster# pasta penne# piskefløde# olivenolie# bacon i skriver# grøn pesto# moxxarellakugler# æg# hvedemel# hvid fisk# fuldkornsmel# rasp# sesamfrø# salt og peber# olie kartofler# hønsebouillon# porre# bacon i tern iceberg salat# agurk# thousand island# risottoris# revet ost# salt# kruspersille# pasta# hakket svinekød# karry# mel# svinebouillon# æbler# ris# mangochutney# bacon i tern"

var ingRef = db.collection('ingredients');

var ingArray = string.split("# ");


for(var i = 0; i < ingArray.length; i++) {
  console.log({name : ingArray[i]});
  var docRef = ingRef.doc(String(i));
  docRef.set({
    name: ingArray[i]
  })
}