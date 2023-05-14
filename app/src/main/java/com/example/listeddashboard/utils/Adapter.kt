package com.example.listeddashboard.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.example.listeddashboard.Model.ListCustom
import com.example.listeddashboard.R
import com.example.listeddashboard.ViewModel.Vm
import com.squareup.picasso.Picasso


class Adapter(context: Context, var data: List<ListCustom>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): ListCustom {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        // Since we don't have unique IDs for our items, we can just use the position as the ID
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.custom_row, parent, false)
        }
        val item = getItem(position)

        // Set the views in the list item layout based on the properties of CustomModel

        //if the length of the string is more then to show a small part with following...
        if(item.title.length>=16){
            view?.findViewById<TextView>(R.id.sampleLink)?.text =
                "${item.title.subSequence(0, 15)}..."
        }
        else {
            view?.findViewById<TextView>(R.id.sampleLink)?.text = item.title
        }
        view?.findViewById<TextView>(R.id.noOfClick)?.text = item.total_clicks.toString()
        val img = view?.findViewById<ImageView>(R.id.imageView2)
        //using picasso to fetch image through the link provided
        Picasso.get().load(item.original_image).into(img)
        view?.findViewById<TextView>(R.id.link)?.text = item.smart_link
        //as date is given in date+time format so cut out a sybstring from that to show date only
        view?.findViewById<TextView>(R.id.date)?.text = item.created_at.subSequence(0,10).toString()

        return view!!
    }

    fun updateData(newData: List<ListCustom>) {
        data = newData
        notifyDataSetChanged()
    }
}
