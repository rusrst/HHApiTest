package com.example.hhapitest.views.requestlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hhapitest.databinding.RequestItemBinding
import com.example.hhapitest.model.data.dataclassesforjson.ShortItem
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
        holder.binding.root.setOnClickListener{
                navigator.launch(DetailsFragment.Screen(), currentItem.alternate_url)
        }
    }

    override fun getItemCount(): Int = items.size




}
