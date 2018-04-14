package com.instafood.projectfood.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class firebaseConnector {
    lateinit var store : FirebaseFirestore
    lateinit var storage : StorageReference

    init {
        store = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://projectfood-9031e.appspot.com/")
    }

    /**
     * Get all recipes from the firebase collection.
     * @param callBack A Lambda function that takes a list of recipes and does something with it.
     * @param onPictureLoad A Lambda function that takes the bitmap when a picture is loaded and handles the bitmap accodringly.
     */
    fun getRecipes(callBack : (List<Recipe>) -> Unit, onPictureLoad : ((Bitmap) -> Unit)?, ingList: List<SelectIngredient>?) {
        val collection = store.collection("recipes")
        collection.get().addOnCompleteListener {
            if(it.isSuccessful) {
                val recList = it.result.documents.fold(emptyList<Recipe>()) {acc, r ->
                    val rec = r.toObject(Recipe::class.java)
                    if(rec != null) {
                        if(!rec.picturePath.equals("")) {
                            var imgRef = storage.child(rec.picturePath)
                            var localFile = File.createTempFile("images", "jpg")
                            imgRef.getFile(localFile).addOnSuccessListener {
                                var bitmap = BitmapFactory.decodeFile(localFile.path)
                                if(onPictureLoad != null) {
                                    onPictureLoad(bitmap)
                                }
                            }.addOnFailureListener({
                            })
                        }
                        acc + rec
                    } else {
                        acc
                    }
                }
                if(ingList != null) {
                    callBack(recList.filter {
                        var containsAll = true;
                        for (sIng in ingList) {
                            if (containsAll) {
                                var contains = false;
                                for (ing in it.ingredients) {
                                    if (ing.title.equals(sIng.name)) {
                                        contains = true
                                    }
                                }
                                containsAll = contains;
                            }
                        }
                        containsAll
                    })
                } else {
                    callBack(recList);
                }
            }
        }
    }

    /**
     * Get all ingredients from the ingredients list in firebase.
     * @param callBack A function to handle the list of ingredients returned from firebase.
     */
    fun getIngredientList(callBack: (List<SelectIngredient>) -> Unit) {
        val collection = store.collection("ingredients")
        collection.get().addOnCompleteListener({
            if(it.isSuccessful) {
                val ingList = it.result.documents.fold(emptyList<SelectIngredient>()) { acc, i ->
                    val ing = i.toObject(SelectIngredient::class.java)
                    if(ing != null) {
                        acc + ing
                    } else {
                        acc
                    }
                }
                callBack(ingList)
            }
        })
    }

}