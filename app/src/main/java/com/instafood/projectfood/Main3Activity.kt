package com.instafood.projectfood

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class Main3Activity : AppCompatActivity() {

    private val checkedFood = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        // Listens to the buttons and runs the isChecked method.
        val button = findViewById<ToggleButton>(R.id.toggleButton)
        button?.setOnCheckedChangeListener { buttonView, isChecked -> isChecked(isChecked, button) }
        val button1 = findViewById<ToggleButton>(R.id.toggleButton2)
        button1?.setOnCheckedChangeListener { buttonView, isChecked -> isChecked(isChecked, button1) }
        val button2 = findViewById<ToggleButton>(R.id.toggleButton3)
        button2?.setOnCheckedChangeListener { buttonView, isChecked -> isChecked(isChecked, button2) }
    }

    // Checks the button and adds it to the list of checked/clicked buttons.
    private fun isChecked(isChecked: Boolean, pbutton: ToggleButton) {
        if (!isChecked) {
            checkedFood.add(pbutton.text.toString())

        } else {
            checkedFood.remove(pbutton.text.toString())
            Toast.makeText(this@Main3Activity, checkedFood.size.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}
