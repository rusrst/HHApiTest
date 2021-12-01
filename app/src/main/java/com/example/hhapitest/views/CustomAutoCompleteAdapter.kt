package com.example.hhapitest.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.foundation.views.BaseViewModel
import com.example.hhapitest.R
import com.example.hhapitest.model.data.AreaRoom
import com.example.hhapitest.views.createrequest.CreateRequestViewModel

class CustomAutoCompleteAdapter(private val context: Context, val viewModel: CreateRequestViewModel) : BaseAdapter(), Filterable{
    private var listItem = mutableListOf<String>()
    private val maxResult = 10
    override fun getCount(): Int {
        return listItem.size
    }

    override fun getItem(position: Int): Any {
        return listItem[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            val newConvertView = layoutInflater.inflate(R.layout.auto_complete_text_view, parent, false)
            return newConvertView.apply {
                findViewById<TextView>(R.id.textViewAutoCompleteTextView).text = listItem[position]
            }
        }
           return convertView.apply {
               findViewById<TextView>(R.id.textViewAutoCompleteTextView)?.text = listItem[position]
           }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filter = FilterResults().apply {
                    if (constraint != null && constraint.length > 0){
                        values = viewModel.getAreasOnNameFromRoomNoLiveData("$constraint%")
                    }
                }
                return filter
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.d("TAG", "publishResult")
                if (results != null){
                    val tempList = results.values as List<AreaRoom>?
                    if (tempList?.size != null) {
                        listItem.clear()
                        tempList.forEach {
                            listItem.add(it.name!!)
                        }
                        notifyDataSetChanged()
                    }
                    else notifyDataSetInvalidated()
                }
                else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}