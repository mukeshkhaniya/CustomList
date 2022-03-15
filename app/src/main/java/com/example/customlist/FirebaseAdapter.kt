package com.example.customlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FirebaseAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<FirebaseAdapter.MyFireBaseViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(postion : Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFireBaseViewHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyFireBaseViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyFireBaseViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.name.text = currentitem.name

        //holder.lastMessage.text = currentitem.lastMessage
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyFireBaseViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.personName)
        //var lastMessage : TextView = itemView.findViewById(R.id.lastMessage)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}