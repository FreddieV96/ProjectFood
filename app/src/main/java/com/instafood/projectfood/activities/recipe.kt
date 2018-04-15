package com.instafood.projectfood.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.instafood.projectfood.R
import com.instafood.projectfood.models.Ingredient
import com.instafood.projectfood.models.Recipe
import com.instafood.projectfood.models.firebaseConnector

import kotlinx.android.synthetic.main.recipe.*
import org.w3c.dom.Text

class recipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe)

        val fbConnector = firebaseConnector()

        val recipeID = intent.getStringExtra("recipeID")

        val imageView = findViewById<ImageView>(R.id.image_item2)
        val titleView = findViewById<Button>(R.id.button2)
        val personView = findViewById<Button>(R.id.button4)
        val IngredientView = findViewById<Button>(R.id.button3)
        val stepView = findViewById<TextView>(R.id.textView2)
        val progressBar = findViewById<ProgressBar>(R.id.loading)

        fbConnector.getRecipe(recipeID, {
            progressBar.visibility = View.GONE
            titleView.setText(it.title);
            personView.setText(it.persons);
            IngredientView.setText(it.ingredients.fold(""){ acc, i -> acc + i + "\n"})
            stepView.setText(it.tutorial)
            imageView.setImageBitmap(it.pictureBM)
        }, true)
        }
    }