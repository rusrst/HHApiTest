package com.example.hhapitest.views.requestlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hhapitest.databinding.RequestItemBinding
import com.example.hhapitest.model.json.dataclassesforjson.ShortItem
import com.example.hhapitest.views.detailsfragment.DetailsFragment

interface UserActionListener {

    fun <T> onRequestMove(request: T, moveBy: Int)

    fun <T> onUserDelete(user: T)

    fun <T> onUserDetails(user: T)

}

class RequestAdapter (private val navigator: RequestListViewModel): RecyclerView.Adapter<RequestAdapter.RequestHolder>() {
 class RequestHolder(val binding: RequestItemBinding): RecyclerView.ViewHolder(binding.root){
 }

    var items: List<ShortItem> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RequestItemBinding.inflate(inflater, parent, false)



        return RequestHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.name.text = currentItem.name
        holder.binding.nameEmployer.text = currentItem.employer?.name
        holder.binding.responsibility.text = currentItem.snippet?.responsibility
        holder.binding.requirement.text = currentItem.snippet?.requirement
        holder.binding.createAt.text = currentItem.created_at?.toDate() ?: ""
        holder.binding.publishedAt.text = currentItem.published_at?.toDate() ?: ""
        holder.binding.root.setOnClickListener{
                navigator.launch(DetailsFragment.Screen(), currentItem.alternate_url)
        }
    }

    override fun getItemCount(): Int = items.size



    private fun String.toDate(): String?{
        val str = this
        val date = str.substringBefore("T")
        val chars = listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
        if (str.length != date.length && testToDate(date, chars)) return date
        return null
    }
    private fun testToDate(str: String, list: List<Char>): Boolean{
        str.forEach { strChar->
            if (strChar !in list) return false
        }
        return true
    }
}
