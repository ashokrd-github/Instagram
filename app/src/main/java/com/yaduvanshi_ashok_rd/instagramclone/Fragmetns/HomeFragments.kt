package com.yaduvanshi_ashok_rd.instagramclone.Fragmetns

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.yaduvanshi_ashok_rd.instagramclone.Adapter.postAdapter
import com.yaduvanshi_ashok_rd.instagramclone.Model.post
import com.yaduvanshi_ashok_rd.instagramclone.R


class HomeFragments : Fragment() {
private lateinit var postAdapter: postAdapter
private lateinit var homeRv:RecyclerView
private lateinit var userList: ArrayList<post>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home_fragments, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeRv = view.findViewById(R.id.Home_rv)
        userList = ArrayList()

        FirebaseDatabase.getInstance().getReference().child("Post").child(Firebase.auth.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children){
                        val post = dataSnapshot.getValue(post::class.java)
                        userList.add(post!!)
                    }
                    postAdapter = postAdapter(userList, requireContext())
                    homeRv.layoutManager = LinearLayoutManager(requireContext())
                    homeRv.adapter = postAdapter

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }



}