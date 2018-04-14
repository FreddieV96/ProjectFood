package com.instafood.projectfood

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main3.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.instafood.projectfood.models.Ingredient
import com.instafood.projectfood.models.Recipe
import java.io.File
import com.bumptech.glide.Glide
import com.instafood.projectfood.R.id.image
import com.instafood.projectfood.adapter.imageAdapter

class recipes : AppCompatActivity() {

    lateinit var collection : CollectionReference
    lateinit var storage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {

        collection = FirebaseFirestore.getInstance().collection("recipes");
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://projectfood-9031e.appspot.com/")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val imageBitmaps : MutableList<Bitmap> = mutableListOf()

        val listView = findViewById<ListView>(R.id.listview1)
        val adapter = imageAdapter(this, imageBitmaps)
        listView.adapter = adapter
        collection
                .get()
                .addOnCompleteListener({
                    if (it.isSuccessful()) {
                        for (document in it.result.documents) {

                            var rec = document.toObject(Recipe::class.java)
                            if (rec != null) {
                                rec.pictureRef = document.get("picture") as? DocumentReference;
                                if (rec.pictureRef != null) {
                                    var imgRef = storage.child(rec.pictureRef!!.path)
                                    var localFile = File.createTempFile("images", "jpg")
                                    imgRef.getFile(localFile).addOnSuccessListener {
                                        var bitmap = BitmapFactory.decodeFile(localFile.path)
                                        imageBitmaps.add(bitmap)
                                        adapter?.notifyDataSetChanged()
                                    }.addOnFailureListener({
                                    })
                                }
                            }

                        }
                    }
                })


    }
}