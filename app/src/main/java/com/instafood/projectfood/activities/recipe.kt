package com.instafood.projectfood.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.instafood.projectfood.R
import com.instafood.projectfood.models.Ingredient
import com.instafood.projectfood.models.firebaseConnector

import kotlinx.android.synthetic.main.recipe.*
import org.w3c.dom.Text

class recipe : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe)

        //val id = intent.getIntExtra("rec",0)

        val imageView = findViewById<ImageView>(R.id.image_item2)
        val titleView = findViewById<Button>(R.id.button2)
        val personView = findViewById<Button>(R.id.button4)
        val IngredientView = findViewById<Button>(R.id.button3)
        val stepView = findViewById<TextView>(R.id.textView2)

        /*var fbConnecter = firebaseConnector()
        val title = "boller i karry"
        fbConnecter.getRecipes({
            var rec = it.get(id)
            titleView.setText(rec.title)
            var number = rec.persons.toString()
            personView.setText("Denne ret er til " + number + "personer.")
            IngredientView.setText("Du skal bruge: \n" + rec.ingredients)
            stepView.setText(rec.tutorial)
        }, {
            imageView.setImageBitmap(it)
    }, null)*/

        }
    }