package com.yaduvanshi_ashok_rd.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.yaduvanshi_ashok_rd.instagramclone.Model.post
import java.util.UUID

class dialogActivity : AppCompatActivity() {
    private lateinit var btnPost: Button
    private lateinit var postImage: ImageView
    private lateinit var btnGallery: Button
    private lateinit var postReel: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var caption:EditText
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        setSupportActionBar(findViewById(R.id.postToolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnPost = findViewById(R.id.btnPostImage)
        postImage = findViewById(R.id.postImage)
        btnGallery = findViewById(R.id.btnGallery)
        postReel = findViewById(R.id.postReel)
        caption = findViewById(R.id.caption)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Image")
        progressDialog.setMessage("post is uploaded")


        btnGallery.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/*  video/*"
            startActivityForResult(galleryIntent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            postImage.setImageURI(data?.data)
        }
        btnPost.setOnClickListener {

        val message = caption.text.toString()

            val messageObjects = post(message, postImage)
            FirebaseDatabase.getInstance().getReference().child("Post").child(Firebase.auth.uid.toString()).push()
                .setValue(messageObjects).addOnSuccessListener {
                    Toast.makeText(this, "post is uploaded Successfully", Toast.LENGTH_SHORT).show()
                }
            uploadPostImage(data?.data)

            progressDialog.show()
        }
        postReel.setOnClickListener {

            uploadPostReel(data?.data)
            progressDialog.show()
            progressDialog.setTitle("Reel")
            progressDialog.setMessage("Reel is uploaded...")
            progressDialog.setCancelable(false)

        }
    }

    private fun uploadPostImage(uri: Uri?) {
        val postImageName = UUID.randomUUID().toString() + "JPG"
        val storageRef = FirebaseStorage.getInstance().getReference().child(Firebase.auth.uid.toString())
            .child("postImage/$postImageName")
        storageRef.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                progressDialog.dismiss()

            }
        }
    }

    private fun uploadPostReel(uri: Uri?){
        val postReelName = UUID.randomUUID().toString()+"mp4"
        val storageRef = FirebaseStorage.getInstance().reference.child(Firebase.auth.uid.toString()).child("PostReels/$postReelName")
        storageRef.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                progressDialog.dismiss()
                FirebaseDatabase.getInstance().getReference("Reels").child(Firebase.auth.uid.toString())
                    .child("PostedReel").setValue(it.toString())
            }
        }

    }
}