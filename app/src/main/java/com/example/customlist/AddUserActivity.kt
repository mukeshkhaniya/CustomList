package com.example.customlist

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.customlist.databinding.AddUserBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import kotlinx.android.synthetic.main.add_user.*
import java.time.LocalDateTime
import java.util.*

class AddUserActivity : AppCompatActivity()  {

    private lateinit var binding : AddUserBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addUser.setOnClickListener {
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

            val i = Intent(this,EditUserActivity::class.java)
            i.putExtra("name",userName)
            i.putExtra("phone",userPhone)
            i.putExtra("country",userCountry)
            i.putExtra("userId",userId)
            startActivity(i)
        }

    }
}