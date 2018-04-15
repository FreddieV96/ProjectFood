package com.instafood.projectfood.activities

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.instafood.projectfood.R
import com.instafood.projectfood.adapter.imageAdapter
import com.instafood.projectfood.models.Ingredient
import com.instafood.projectfood.models.Recipe
import com.instafood.projectfood.models.SelectIngredient
import com.instafood.projectfood.models.firebaseConnector

class recipes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipes)


        val ingredients = intent.getStringArrayListExtra("ing").fold(emptyList<Ingredient>()) { acc, s ->
            acc + Ingredient(s, "0")
        }

        val fbConnector = firebaseConnector()

        val recipeList : MutableList<Recipe> = mutableListOf()

        val listView = findViewById<ListView>(R.id.listview1)
        val adapter = imageAdapter(this, recipeList)
        listView.adapter = adapter

        fbConnector.getRecipes({
            Log.d("fb", it.title)
            recipeList.add(it)
            adapter?.notifyDataSetChanged()
        }, ingredients)

    }
}