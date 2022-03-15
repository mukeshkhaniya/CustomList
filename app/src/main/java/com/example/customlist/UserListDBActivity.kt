package com.example.customlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customlist.databinding.ActivityUserBinding
import com.google.firebase.database.*

class UserListDBActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var useRecycleView : RecyclerView
    private lateinit var userArrayList : ArrayList<User>

    private lateinit var binding : ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.db_list)

        useRecycleView = findViewById(R.id.dblistview)

        useRecycleView.layoutManager = LinearLayoutManager(this)
        useRecycleView.setHasFixedSize(true)

        userArrayList = arrayListOf<User>()
        getUserData()

    }

    private fun getUserData(){
        dbref = FirebaseDatabase.getInstance().getReference("message")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(snapshot in snapshot.children){
                        val user = snapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                    var adapter  = FirebaseAdapter(userArrayList)
                    useRecycleView.adapter = adapter
                    adapter.setOnItemClickListener(object : FirebaseAdapter.onItemClickListener {
                        override fun onItemClick(postion: Int) {

                            val intent = Intent(this@UserListDBActivity, EditUserActivity::class.java)
                            intent.putExtra("name", userArrayList[postion].name)
                            intent.putExtra("phone", userArrayList[postion].phoneNo)
                            intent.putExtra("country", userArrayList[postion].country)
                            intent.putExtra("userId", userArrayList[postion].userId)
                            startActivity(intent)
                        }
                    });
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}