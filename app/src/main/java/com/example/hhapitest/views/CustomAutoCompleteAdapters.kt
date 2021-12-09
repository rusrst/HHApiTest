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
import com.example.foundation.model.SuccessResult
import com.example.foundation.model.tasks.Task
import com.example.foundation.model.tasks.dispatchers.MainThreadDispatcher
import com.example.hhapitest.R
import com.example.hhapitest.model.data.dataclassesforjson.AreaRoom
import com.example.hhapitest.model.data.dataclassesforjson.EmployerRequest
import com.example.hhapitest.model.json.Json
import com.example.hhapitest.views.createrequest.CreateRequestViewModel

class CustomAutoCompleteAdapterAreas(private val context: Context, val viewModel: CreateRequestViewModel) : BaseAdapter(), Filterable{
    private var listItem = listOf<AreaRoom>()

    fun getListItemById (position: Int): AreaRoom {
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
class CustomAutoCompleteAdapterEmployers(private val context: Context, val viewModel: CreateRequestViewModel) : BaseAdapter(), Filterable{
    private var currentTask: Task<String>? = null
    private var listItem = listOf<EmployerRequest>()

    fun getListItemById (position: Int): EmployerRequest {
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
                        currentTask?.cancel()
                        currentTask = viewModel.getEmployersRequestNoLiveData(constraint.toString())
                        currentTask?.enqueue(MainThreadDispatcher()){ result ->
                            if (result is SuccessResult && result.data != ""){
                                try {
                                    listItem =
                                        Json.getListEmployersForRequestList(result.data) ?: listOf()
                                }
                                catch (e: Exception)
                                {
                                    listItem = listOf()

                                }
                            } else listItem = listOf()
                            notifyDataSetChanged()
                        }
                    }
                }
                return filter
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            }
        }
    }
}