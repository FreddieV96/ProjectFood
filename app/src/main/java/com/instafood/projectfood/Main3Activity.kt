package com.instafood.projectfood

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import com.google.android.flexbox.FlexboxLayout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.instafood.projectfood.models.Ingredient
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_main3.view.*

class Main3Activity : AppCompatActivity() {

    private val checkedFood = mutableListOf<String>()
    lateinit var collection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        collection = FirebaseFirestore.getInstance().collection("ingredients")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        collection
                .get()
                .addOnCompleteListener({
                    if (it.isSuccessful) {
                        for (document in it.result.documents) {
                            val ing = document.get("name")
                            if (ing != null) {
                                //val ll_main = findViewById<LinearLayout>(R.id.ll_main_layout)
                                val flex = findViewById(R.id.flex_layout) as FlexboxLayout
                                val btn_D = ToggleButton(this)
                                btn_D.setChecked(true)

                                //btn_D.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                                btn_D.layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                                btn_D.text = ing.toString()
                                btn_D.textOff = ing.toString()
                                btn_D.textOn = ing.toString()
                                btn_D.setOnCheckedChangeListener { _, isChecked -> isChecked(isChecked, btn_D) }

                                //ll_main.addView(btn_D)
                                flex.addView(btn_D)
                            }

                        }
                    }
                })

    }

    // Checks the button and adds it to the list of checked/clicked buttons.
    private fun isChecked(isChecked: Boolean, pbutton: ToggleButton) {
        if (!isChecked) {
            checkedFood.add(pbutton.text.toString())
            Toast.makeText(this@Main3Activity, checkedFood.size.toString(), Toast.LENGTH_SHORT).show()

        } else {
            checkedFood.remove(pbutton.text.toString())
            Toast.makeText(this@Main3Activity, checkedFood.size.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_next -> {
                var i = 0;
                for (str in checkedFood) {
                    Toast.makeText(this@Main3Activity, checkedFood[i], Toast.LENGTH_SHORT).show()
                    i += 1;
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
