package com.instafood.projectfood

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {

    lateinit var collection : CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {

        collection = FirebaseFirestore.getInstance().collection("test");
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val button = findViewById<Button>(R.id.button2);

        val textField = findViewById<TextView>(R.id.textView);

        collection
                .get()
                .addOnCompleteListener({
                    if(it.isSuccessful()) {
                        for(document in it.result) {
                            textField.append(document.data.get("born").toString() + "\n")
                        }
                    }
                })


        button.setOnClickListener({view : View? -> click()});

    }


    private fun click() {
        val textField = findViewById<TextView>(R.id.textView);
        collection
                .get()
                .addOnCompleteListener({
                    if(it.isSuccessful()) {
                        for(document in it.result) {
                            textField.append(document.data.get("born").toString())
                            //val toast = Toast.makeText(applicationContext, document.data.get("born").toString(), Toast.LENGTH_SHORT);
                            //toast.show()
                        }
                    }
                })
    }

}
