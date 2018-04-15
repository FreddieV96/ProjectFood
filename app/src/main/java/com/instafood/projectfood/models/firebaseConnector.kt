package com.instafood.projectfood.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import kotlin.math.sin

class firebaseConnector {
    lateinit var store : FirebaseFirestore
    lateinit var storage : StorageReference

    /**
     * Get a single recipe from the firestore.
     * @param id The id to get from the firestore.
     * @param callBack The callback function to run once the recipe is loaded.
     */
    fun getRecipe(id: String, callBack : (Recipe) -> Unit) {
        val document = FirebaseFirestore.getInstance().collection("recipes").document(id)
        document.get().addOnCompleteListener({
            if(it.isSuccessful) {
                fromSnapshotToRecipe(it.result, callBack)
            }
        })
    }

    /**
     * Get all recipes from the firebase collection.
     * @param callBack A Lambda function that takes a list of recipes and does something with it.
     * @param onPictureLoad A Lambda function that takes the bitmap when a picture is loaded and handles the bitmap accodringly.
     */
    fun getRecipes(callBack: (Recipe) -> Unit, ingList: List<Ingredient> = emptyList()) {
        val collection = FirebaseFirestore.getInstance().collection("recipes")
        collection.get().addOnCompleteListener( {
            if(it.isSuccessful) {
                it.result.documents.forEach({
                    if(ingList.isNotEmpty()) {
                        val recipe = it.toObject(Recipe::class.java)
                        if(recipe != null) {
                            val sIngredients = recipe.ingredients.fold(emptyList<String>()) { acc, e ->
                                acc + e.title
                            }
                            val ssIngredients = ingList.fold(emptyList<String>()) { acc,e ->
                                acc + e.title
                            }
                            if(sIngredients.containsAll(ssIngredients)) {
                                fromSnapshotToRecipe(it, callBack)
                            }
                        }
                    } else {
                        fromSnapshotToRecipe(it, callBack)
                    }
                })
            }
        })
    }

    /**
     * Converts DocumentSnapshot to a recipe and calls a function on the recipe.
     * @param it The snapshot to convert from.
     * @param callBack The callback to run on the recipe.
     */
    fun fromSnapshotToRecipe(it: DocumentSnapshot, callBack : (Recipe) -> Unit) {
            val rec = it.toObject(Recipe::class.java)
            if(rec != null) {
                rec.id = it.id
                if(!rec.picturePath.equals("")) {
                    val imgRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://projectfood-4a086.appspot.com/").child(rec.picturePath)
                    val localFile = File.createTempFile("images", "jpg")
                    imgRef.getFile(localFile).addOnSuccessListener {
                        var bitmap = BitmapFactory.decodeFile(localFile.path)
                        rec.pictureBM = bitmap;
                        callBack(rec)
                    }
                } else {
                    callBack(rec)
                }
            }
    }

    /**
     * Get all ingredients from the ingredients list in firebase.
     * @param callBack A function to handle the list of ingredients returned from firebase.
     */
    fun getIngredientList(callBack: (List<Ingredient>) -> Unit) {
        val collection = FirebaseFirestore.getInstance().collection("recipes")
        collection.get().addOnCompleteListener({
            if(it.isSuccessful) {
                val ingList = mutableListOf<Ingredient>()
                val ingListTitle = mutableListOf<String>()
                it.result.documents.forEach({
                    val rec = it.toObject(Recipe::class.java)
                    if(rec != null) {
                        rec.ingredients.forEach({
                            if(!ingListTitle.contains(it.title)) {
                                ingList.add(it)
                                ingListTitle.add(it.title)
                            }
                        })
                    }
                })
                callBack(ingList.toList())
            }
        })
    }

}