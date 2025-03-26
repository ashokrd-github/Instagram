package com.yaduvanshi_ashok_rd.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.yaduvanshi_ashok_rd.instagramclone.Model.User

class SignupActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signup: Button
    private lateinit var textView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        name = findViewById(R.id.Name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        signup = findViewById(R.id.signup)
        textView = findViewById(R.id.textView)
        auth = Firebase.auth

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Signing")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCancelable(false)

        signup.setOnClickListener {
            progressDialog.show()
            val name = name.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()
            singup(email, password, name)
        }
        textView.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }
    private fun singup(email:String, password:String, name:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener (this){task->
                finish()
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    val user = User(
                        userName = name,
                        userEmail = email,
                        userPassword = password,
                        userProfile = " ",
                        userID = auth.uid.toString()
                    )
                    uploadUserDataOnDatabase(user)
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    Toast.makeText(this, "Signup Successfully", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun uploadUserDataOnDatabase(user: User){
    FirebaseDatabase.getInstance().getReference("Users").child(user.userID).setValue(user)
        Toast.makeText(this, "post is uploaded", Toast.LENGTH_SHORT).show()
    }
}