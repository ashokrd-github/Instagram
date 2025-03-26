package com.yaduvanshi_ashok_rd.instagramclone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yaduvanshi_ashok_rd.instagramclone.Model.post
import com.yaduvanshi_ashok_rd.instagramclone.R

class postAdapter(
    private val userList: ArrayList<post>,
    private val context: Context,
): RecyclerView.Adapter<postAdapter.ViewHolder>(){

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val userPost: ImageView = itemView.findViewById(R.id.userPost)
        val userProfile1 : ImageView = itemView.findViewById(R.id.userprofile1)
        var userName: TextView = itemView.findViewById(R.id.userName1)
        var userCaption:TextView = itemView.findViewById(R.id.userCaption)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userName.text = currentUser.userName

        Glide.with(context).load(currentUser.userProfile).into(holder.userProfile1)



    }

}