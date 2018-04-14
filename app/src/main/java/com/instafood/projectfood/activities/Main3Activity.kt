package com.instafood.projectfood.activities

import android.app.ListActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.instafood.projectfood.R
import com.instafood.projectfood.models.Recipe
import com.instafood.projectfood.models.SelectIngredient
import com.instafood.projectfood.models.firebaseConnector
import kotlinx.android.synthetic.main.activity_main3.view.*
import java.util.*

class Main3Activity : AppCompatActivity() {

    lateinit var collection : CollectionReference
    lateinit var storage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main3)

        val textView = findViewById<TextView>(R.id.textView3)
        val fbConnector = firebaseConnector()
        fbConnector.getRecipes({
            for(rec in it) {
                textView.append(rec.title + "\n")
            }
        }, {
        }, null)

        /*collection = FirebaseFirestore.getInstance().collection("recipes");
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://projectfood-9031e.appspot.com/")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val imageBitmaps : MutableList<Bitmap> = mutableListOf()

        val textView = findViewById<TextView>(R.id.textView)
        val listView = findViewById<ListView>(R.id.listview)
        val adapter = imageAdapter(this, imageBitmaps)
        listView.adapter = adapter
        collection
                .get()
                .addOnCompleteListener({
                    if (it.isSuccessful()) {
                        for (document in it.result.documents) {
                            var rec = document.toObject(Recipe::class.java)
                            if (rec != null) {
                                textView.text = rec.title
                                rec.pictureRef = document.get("picture") as? DocumentReference;
                                if (rec.pictureRef != null) {
                                    var imgRef = storage.child(rec.pictureRef!!.path)
                                    var localFile = File.createTempFile("images", "jpg")
                                    imgRef.getFile(localFile).addOnSuccessListener {
                                        var bitmap = BitmapFactory.decodeFile(localFile.path)
                                        //imageBitmaps.add(bitmap)
                                        //adapter?.notifyDataSetChanged()
                                    }.addOnFailureListener({
                                    })
                                }
                            }

                        }
                    }
                })*/


        }
}
