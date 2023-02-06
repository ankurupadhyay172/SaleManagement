package com.ankurupadhyay.salemanagement.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ankurupadhyay.salemanagement.R
import com.ankurupadhyay.salemanagement.data.Variants
import com.ankurupadhyay.salemanagement.utils.setPrice

class SpinnerAdapter(context: Context,resource:Int,list: List<Variants>):ArrayAdapter<Variants>(context,resource,list)
{
    var inflater:LayoutInflater
    var mlist:List<Variants>
    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mlist = list
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if(view==null)
        {
            view = inflater.inflate(R.layout.item_variant_layout,parent,false)
        }

        val title = view?.findViewById<TextView>(R.id.title)
        val desc = view?.findViewById<TextView>(R.id.description)
        val price = view?.findViewById<TextView>(R.id.price)

        title?.text = mlist[position].name
        desc?.text = mlist[position].description
        price?.setPrice(mlist[position].sellingPrice)

        return view!!
    }
}