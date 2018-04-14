package com.instafood.projectfood.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
        val title: String = "",
        val persons: String = "",
        val tutorial: String = "",
        val picturePath: String = "",
        var pictureBM: Bitmap? = null,
        val ingredients: ArrayList<Ingredient> = ArrayList()
) : Parcelable