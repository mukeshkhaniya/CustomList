package com.example.customlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.customlist.databinding.ActivityMainBinding
import com.example.customlist.databinding.LandingPageBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_user.*
import kotlinx.android.synthetic.main.landing_page.*
import kotlinx.android.synthetic.main.landing_page.addCountry
import kotlinx.android.synthetic.main.landing_page.addPhone
import kotlinx.android.synthetic.main.landing_page.addPrsName
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding : LandingPageBinding
    private lateinit var userAarrayList : ArrayList<User>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var addUsrButton = findViewById<Button>(R.id.addUser)
        addUsrButton.setOnClickListener{
            var userName = addPrsName.text.toString()
            var userPhone = addPhone.text.toString()
            var userCountry = addCountry.text.toString()
            var userId = UUID.randomUUID()
            // Write a message to the database
            val database = Firebase.database

            val myRef = database.getReference("message")
            myRef.child(userId.toString()).setValue(User(userName,"User Created!",
                LocalDateTime.now().toString(),userPhone,userCountry,123,UUID.randomUUID().toString())
            )
            var intent = Intent(this,UserListDBActivity::class.java)
            startActivity(intent)
        }

        var goToListBtn = findViewById<Button>(R.id.toList)
        goToListBtn.setOnClickListener{
            var intent = Intent(this,UserListDBActivity::class.java)
            startActivity(intent)
        }




        /*val imageId = intArrayOf(
            R.drawable.albert,R.drawable.austin,R.drawable.jake,R.drawable.joseph,R.drawable.stefan
        )

        val name = arrayOf(
            "Albert",
            "Austin",
            "Jake",
            "Joseph",
            "Stefan"
        )


        var lastMessage = arrayOf(
            "Hey" , "Supp" , "Let's Catchup" , "Dinner tonight?", "Party Today?"
        )

        var lastmsgTime = arrayOf(
            "8:45 pm","9:00 am","7:34 pm","6:32 am","5:45 am"
        )

        var phoneNo = arrayOf(
            "55654665","453653","4523452","234535234","21123233"
        )

        var country = arrayOf(
            "USA","Russia","India","Germany","France"
        )

        userAarrayList = ArrayList()
        for(i in name.indices){
            var user = User(name[i], lastMessage[i], lastmsgTime[i],phoneNo[i],country[i],imageId[i] ,UUID.randomUUID().toString())
            userAarrayList.add(user)
        }

        *//*val database = Firebase.database
        val myRef = database.getReference("message")
        this.database = Firebase.database.reference






        var getData = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for(i in snapshot.children){
                    var usrName = i.child("name").getValue()
                    var lastMsg = i.child("lastMsgTime").getValue()
                    var lastMsgTime = i.child("lastMsgTime").getValue()
                    var phNum = i.child("phoneNo").getValue()
                    var country = i.child("country").getValue()
                    var imageId = i.child("imageId").getValue()
                    var usrId = i.child("userId").getValue()
                    var user = User(usrName.toString(), lastMsg.toString(), lastMsgTime.toString(),
                        phNum.toString(), country.toString(), Integer.parseInt(imageId.toString()),usrId.toString())
                    userAarrayList.add(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)*//*

        binding.listview.isClickable = true
        //binding.listview.adapter = MyAdapter(this, userAarrayList)
        *//*binding.listview.setOnItemClickListener { parent, view, position, id ->

            val name = name[position]
            val phone = phoneNo[position]
            val country = country[position]
            val imageId = imageId[position]

            val i = Intent(this,UserListDBActivity::class.java)
            i.putExtra("name",name)
            i.putExtra("phone",phone)
            i.putExtra("country",country)
            i.putExtra("imageId",imageId)
            startActivity(i)
        }*/
    }
}