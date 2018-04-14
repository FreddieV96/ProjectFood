package com.instafood.projectfood.adapter

import android.view.ViewGroup
import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import com.instafood.projectfood.R
import com.instafood.projectfood.models.Recipe

class imageAdapter(private val context: Activity, private val recipeList : List<Recipe>)
    : BaseAdapter() {
    override fun getView(id: Int, view: View?, viewgroup: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.imagelayout, null)
        val button = rowView.findViewById<Button>(R.id.button)
        val imageView = rowView.findViewById<ImageView>(R.id.image_item)
        if (recipeList[id].pictureBM != null) {
            imageView.setImageBitmap(recipeList[id].pictureBM)
        }
        button.setOnClickListener {
            
        }

        return rowView

    }

    override fun getItem(p0: Int): Any {
        return recipeList.get(p0)
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return recipeList.size
    }
}
