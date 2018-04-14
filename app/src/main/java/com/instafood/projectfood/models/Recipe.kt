package com.instafood.projectfood.models

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentReference

data class Recipe(
        val title: String = "",
        val persons: String = "",
        val tutorial: String ="",
        val picturePath : String = "",
        var pictureBM : Bitmap? = null,
        val ingredients: ArrayList<Ingredient> = ArrayList()
)