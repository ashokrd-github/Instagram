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
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.ProducerScope
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var textView: TextView
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        textView = findViewById(R.id.textView)
        auth = Firebase.auth
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)

        login.setOnClickListener {
            progressDialog.show()
            val email = email.text.toString()
            val password = password.text.toString()
            login(email, password)
        }
        textView.setOnClickListener {
            startActivity(
                Intent(this, SignupActivity::class.java)
            )
        }

    }
    private fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    finish()
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    Toast.makeText(this, "Logged Successfully", Toast.LENGTH_SHORT).show()
                }
                else{
                    dismissDialog(101)
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
}