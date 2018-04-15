package com.instafood.projectfood.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import com.google.android.flexbox.FlexboxLayout
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.instafood.projectfood.R
import com.instafood.projectfood.models.SelectIngredient
import com.instafood.projectfood.models.firebaseConnector
import kotlinx.android.synthetic.main.activity_main3.*
import android.R.attr.key
import android.util.Log


class Main3Activity : AppCompatActivity() {

    private val checkedFood = mutableSetOf<String>()
    private val queryList = mutableListOf<SelectIngredient>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        val fbConnector = firebaseConnector()

        fbConnector.getIngredientList {
            val sortedList = it.sortedWith(compareBy({ it.name }))
            creatButtons(sortedList)

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val sSV = findViewById<SearchView>(R.id.searchView)

            sSV.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            sSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    for (ing in it) {
                        if (ing.name.contains(newText))
                            queryList.add(ing)
                    }
                    if (queryList.size > 0) {
                        resetView()
                    }
                    val sortedList = queryList.sortedWith(compareBy({ it.name }))
                    creatButtons(sortedList)

                    queryList.removeAll(it)
                    return false
                }
            })
        }
    }

    private fun creatButtons(it: List<SelectIngredient>) {
        for (ing in it) {
                    val name = ing.name
                    val flex = findViewById(R.id.flex_layout) as FlexboxLayout
                    val btn_D = ToggleButton(this)
                    if(checkedFood.contains(name)){
                    btn_D.setChecked(true)}
                            else{
                        btn_D.setChecked(false)
                    }

                    //btn_D.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    btn_D.layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                    btn_D.text = name
                    btn_D.textOff = name
                    btn_D.textOn = name
                    btn_D.setOnCheckedChangeListener { _, isChecked -> isChecked(isChecked, btn_D) }

                    //ll_main.addView(btn_D)
                    flex.addView(btn_D)

        }
    }

    private fun resetView(){
        val flex = findViewById(R.id.flex_layout) as FlexboxLayout
        flex.removeAllViews()
    }

    // Checks the button and adds it to the list of checked/clicked buttons.
    private fun isChecked(isChecked: Boolean, pbutton: ToggleButton) {
        if (isChecked) {
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

//        val intent = Intent(this@Main3Activity,recipes::class.java);
//        val extras = intent.extras
//        extras.putStringArrayList("ingredients", ArrayList(checkedFood))

        val intent = Intent(this, recipes::class.java)
        intent.putExtra("ing", ArrayList(checkedFood))
        startActivity(intent)


        return true
//        return when (item.itemId) {
//            R.id.action_next -> {
//                var i = 0;
//                for (str in checkedFood) {
//                    Toast.makeText(this@Main3Activity, checkedFood[i], Toast.LENGTH_SHORT).show()
//                    i += 1;
//                }
//                return true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }


    }
}

