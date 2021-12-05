package com.example.hhapitest.views

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.hhapitest.R
import com.example.hhapitest.model.data.AreaRoom
import com.example.hhapitest.views.createrequest.CreateRequestViewModel

class CustomAutoCompleteAdapter(private val context: Context, val viewModel: CreateRequestViewModel) : BaseAdapter(), Filterable{
    private var listItem = listOf<AreaRoom>()

    fun getListItemById (position: Int): AreaRoom{
        return listItem[position]
    }

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
                findViewById<TextView>(R.id.textViewAutoCompleteTextView).apply {
                    text = listItem[position].name
                    gravity = Gravity.CENTER
                }
            }
        }
           return convertView.apply {
               findViewById<TextView>(R.id.textViewAutoCompleteTextView)?.apply {
                   text = listItem[position].name
                   gravity = Gravity.CENTER
               }
           }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filter = FilterResults().apply {
                    if (constraint != null && constraint.isNotEmpty()){
                        values = viewModel.getAreasOnNameFromRoomNoLiveData("$constraint%")
                    }
                }
                return filter
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null){
                    val tempList = results.values as List<*>?
                    if (tempList?.size != null) {
                        listItem = tempList as List<AreaRoom>
                        notifyDataSetChanged()
                    }
                    else {
                        notifyDataSetInvalidated()
                        listItem = listOf()
                    }
                }
               else {
                    listItem = listOf()
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}