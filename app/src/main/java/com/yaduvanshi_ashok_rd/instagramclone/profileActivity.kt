package com.yaduvanshi_ashok_rd.instagramclone

import android.app.Dialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.provider.MediaStore
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class profileActivity : AppCompatActivity() {
    private lateinit var openDialog: ImageView
    private lateinit var dialog: Dialog
    private lateinit var gallery: ImageView
    private lateinit var camera: ImageView
    private lateinit var delete: ImageView
    private lateinit var profile: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.Ptoolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openDialog = findViewById(R.id.openDialog)
        profile = findViewById(R.id.profile)


        openDialog.setOnClickListener {
            showDialog()
            dialog.show()


        }

    }
    private fun showDialog(){
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog)

        gallery = dialog.findViewById(R.id.gallery)
        camera = dialog.findViewById(R.id.camera)
        delete = dialog.findViewById(R.id.delete)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 102)

        gallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/* video/*"
            startActivityForResult(galleryIntent, 101)
        }

        camera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            cameraIntent.type = "image/* video/*"
            startActivityForResult(cameraIntent, 102)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==101 && resultCode== RESULT_OK){
            profile.setImageURI(data?.data)
            uploadProfileImage(data?.data)
            dialog.dismiss()
        }

    }
private fun uploadProfileImage(uri: Uri?){
    val profileImageName = UUID.randomUUID().toString() +"jpg"
    val storageRef = FirebaseStorage.getInstance().getReference().child("profileImage/$profileImageName")
    storageRef.putFile(uri!!).addOnSuccessListener {
        val result = it.metadata?.reference?.downloadUrl
        result?.addOnCompleteListener {
            FirebaseDatabase.getInstance().reference.child("Users").child(Firebase.auth.uid.toString())
                .child("userProfile").setValue(it.toString())
            Toast.makeText(this, "profile Image update", Toast.LENGTH_SHORT).show()
        }
    }

}



}