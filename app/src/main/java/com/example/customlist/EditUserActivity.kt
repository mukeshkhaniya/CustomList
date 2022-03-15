package com.example.customlist

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.add_user.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.customlist.databinding.EditUserBinding
import com.google.firebase.database.MutableData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.edit_user.*
import kotlinx.android.synthetic.main.list_item.*
import java.time.LocalDateTime
import java.util.*

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding : EditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val country = intent.getStringExtra("country")
        val userId = intent.getStringExtra("userId")
        val imageId = intent.getIntExtra("imageId",R.drawable.stefan)

        binding.editPrsName.setText(name)
        binding.editCountry.setText(country)
        binding.editPhone.setText(phone)
        binding.editUserId.setText(userId)
        binding.editSMS.setEnabled(false)
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS), 111)
        }else{
            receiveMsg()
        }

        editUser.setOnClickListener {
            var getEditSMS = editSMS.text.toString()

            val name = editPrsName.text.toString();
            val phNo = editPhone.text.toString()
            val cntry = editCountry.text.toString()
            val usrId = editUserId.text.toString()
            getEditSMS = "Do you wish to subscribe for notification?"
            val database = Firebase.database
            val myRef = database.getReference("message")
            myRef.child(usrId.toString()).setValue(User(name,getEditSMS.toString(),
                LocalDateTime.now().toString(),phNo,cntry,123, userId)
            )

            sendSMS(getEditSMS,phNo)
            /*val permissionRequest = 101
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                var sms = SmsManager.getDefault()
                val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
                sms.sendTextMessage(phNo,null,getEditSMS,sentPI,null)
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.SEND_SMS), permissionRequest
                )
            }*/



            var intent = Intent(this,UserListDBActivity::class.java)
            startActivity(intent)

        }

    }

    private fun sendSMS(textMessage: String, phNo: String){
        if(textMessage != null && !textMessage.isEmpty()){
            val permissionRequest = 101
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                var sms = SmsManager.getDefault()
                val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
                sms.sendTextMessage(phNo,null,textMessage,sentPI,null)
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.SEND_SMS), permissionRequest
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            receiveMsg()
        }


    }

    private fun receiveMsg() {
        var br = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){

                    for(sms : SmsMessage in Telephony.Sms.Intents.getMessagesFromIntent(p1)){
                        Toast.makeText(applicationContext,sms.displayMessageBody, Toast.LENGTH_LONG).show()

                        if(sms.displayMessageBody.contains("YES")){
                            editPhone.setText(sms.originatingAddress)
                            editSMS.setText(sms.displayMessageBody)
                            binding.editSMS.setText(sms.displayMessageBody)
                        }else{
                            editSMS.setText("Nothing to Show")
                            binding.editSMS.setText(sms.displayMessageBody)
                        }

                    }
                    /* for(sms : SmsMessage! in Telephony.Sms.Intents.getMessagesFromIntent(p1)){
                         Toast.makeText(applicationContext,sms.displayMessageBody,Toast.LENGTH_LONG).show()
                     }*/
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
        //Update the firebase db


    }
}