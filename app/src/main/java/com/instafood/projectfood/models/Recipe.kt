package com.instafood.projectfood.models

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference

data class Recipe(
        val title: String,
        val persons: String,
        val tutorial: String,
        val picturePath: String,
        var pictureBM: Bitmap?,
        val ingredients: ArrayList<Ingredient>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Bitmap::class.java.classLoader),
            parcel.createTypedArrayList(Ingredient.CREATOR))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(persons)
        parcel.writeString(tutorial)
        parcel.writeString(picturePath)
        parcel.writeParcelable(pictureBM, flags)
        parcel.writeTypedList(ingredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}