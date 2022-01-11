package com.example.hhapitest.views.listofroomrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hhapitest.databinding.RequestItemBinding
import com.example.hhapitest.model.data.database.dataclassroom.RequestRoom
import com.example.hhapitest.views.requestlist.RequestList

class ListOfRoomRequestAdapter (val navigator: ListOfRoomRequestViewModel):
    RecyclerView.Adapter<ListOfRoomRequestAdapter.ListOfRoomRequestHolder>() {
    var items: List<RequestRoom> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }
    class ListOfRoomRequestHolder(val binding: RequestItemBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfRoomRequestHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RequestItemBinding.inflate(inflater, parent, false)


        return ListOfRoomRequestHolder(binding)
    }

    override fun onBindViewHolder(holder: ListOfRoomRequestHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.run {
            name.text = currentItem.name
            nameEmployer.text = currentItem.employerName
        }
        holder.binding.root.setOnClickListener {
            if (holder.bindingAdapterPosition != RecyclerView.NO_POSITION){
                navigator.launch(RequestList.Screen(), currentItem.request)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}