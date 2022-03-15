package com.example.customlist

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(private val context : Activity, private val arrayList : ArrayList<User>) :
    ArrayAdapter<User>(context,R.layout.list_item,arrayList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{

        var inflater : LayoutInflater = LayoutInflater.from(context)
        var view : View = inflater.inflate(R.layout.list_item, null)

        val imageView : ImageView = view.findViewById(R.id.profile_pic)
        val userName : TextView = view.findViewById(R.id.personName)
        val lastMsg : TextView = view.findViewById(R.id.lastMessage)
        val lastMsgTime : TextView = view.findViewById(R.id.msgTime)

        //imageView.setImageResource(arrayList[position].imageId.toInt())
        userName.text = arrayList[position].name
        lastMsg.text = arrayList[position].lastMessage
        lastMsgTime.text = arrayList[position].lastMsgTime

        return view

    }
}