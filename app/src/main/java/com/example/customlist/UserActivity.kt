package com.example.customlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.customlist.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val country = intent.getStringExtra("country")
        val imageId = intent.getIntExtra("imageId",R.drawable.stefan)

        binding.nameProfile.text = name
        binding.countryProfile.text = country
        binding.phoneProfile.text = phone
        binding.profileImage.setImageResource(imageId)

        binding.backBtn.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        binding.addButton.setOnClickListener {
            val i = Intent(this,AddUserActivity::class.java)
            startActivity(i)
        }

    }


}