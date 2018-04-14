package com.instafood.projectfood.adapter

import android.view.ViewGroup
import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import com.instafood.projectfood.R

class imageAdapter(private val context: Activity, private val bitMaps: List<Bitmap>)
    : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.imagelayout,null)
        val imageView = rowView.findViewById<ImageView>(R.id.image_item)
        imageView.setImageBitmap(bitMaps[p0])
        return rowView
    }
    override fun getItem(p0: Int): Any {
        return bitMaps.get(p0)
    }
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    override fun getCount(): Int {
        return bitMaps.size
    }
}