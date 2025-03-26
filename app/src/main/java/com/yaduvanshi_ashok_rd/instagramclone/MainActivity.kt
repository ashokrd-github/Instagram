package com.yaduvanshi_ashok_rd.instagramclone

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yaduvanshi_ashok_rd.instagramclone.Fragmetns.AddFragment
import com.yaduvanshi_ashok_rd.instagramclone.Fragmetns.HomeFragments
import com.yaduvanshi_ashok_rd.instagramclone.Fragmetns.profileFragments

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        window.statusBarColor = Color.TRANSPARENT
    auth = Firebase.auth


        val bottomBar : BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    openFragments(HomeFragments())
                    Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.add->{

                    openFragments(AddFragment())

                    Toast.makeText(this, "Add Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.profile->{
                    openFragments(profileFragments())
                    Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        false
    }

    private fun openFragments(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.FragmentsCv, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logOut->{
                auth.signOut()
                startActivity(
                    Intent(this, SignupActivity::class.java)
                )
                Toast.makeText(this, "Logout successfully", Toast.LENGTH_SHORT).show()
            }
            R.id.profile->{
                startActivity(
                    Intent(this, profileActivity::class.java)
                )
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.account->{
                Toast.makeText(this, "Account clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.settings->{
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return  true
    }

}