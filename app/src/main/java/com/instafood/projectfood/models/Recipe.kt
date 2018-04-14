package com.instafood.projectfood.models

import com.google.firebase.firestore.DocumentReference

data class Recipe(
        val title: String = "",
        val persons: String = "",
        val tutorial: String ="",
        var pictureRef : DocumentReference? = null,
        val ingredients: ArrayList<Ingredient> = ArrayList()
)