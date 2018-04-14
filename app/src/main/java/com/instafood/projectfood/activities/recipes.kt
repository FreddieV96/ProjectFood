package com.instafood.projectfood.activities

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.instafood.projectfood.R
import com.instafood.projectfood.adapter.imageAdapter
import com.instafood.projectfood.models.Recipe
import com.instafood.projectfood.models.SelectIngredient
import com.instafood.projectfood.models.firebaseConnector

class recipes : AppCompatActivity() {

    lateinit var collection : CollectionReference
    lateinit var storage : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {

        collection = FirebaseFirestore.getInstance().collection("recipes");
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://projectfood-9031e.appspot.com/")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipes)



        val ingredients = intent.getStringArrayListExtra("ing").fold(emptyList<SelectIngredient>()) { acc, s ->
            acc + SelectIngredient(s)
        }

        Toast.makeText(applicationContext, ingredients.toString(), Toast.LENGTH_SHORT).show()

        val fbConnector = firebaseConnector()

        val recipeList : MutableList<Recipe> = mutableListOf()

        val listView = findViewById<ListView>(R.id.listview1)
        val adapter = imageAdapter(this, recipeList)
        listView.adapter = adapter

        fbConnector.getRecipes({}, {
            recipeList.add(it)
            adapter?.notifyDataSetChanged()
        }, ingredients)
    }
}