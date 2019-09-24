package com.sheldon.bujofe

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sheldon.bujofe.databinding.ActivityMainBinding
import com.sheldon.bujofe.login.LoginActivity


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var textMessage: TextView
    private val TAG: String = "MainActivity"



    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val navController = this.findNavController(R.id.navHostFragment)
            when (item.itemId) {
                R.id.navigation_home -> {
                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_calendar -> {
                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_calendarFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_shelf_read_room_status -> {
                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_studyRoomFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_replacement_lesson_list -> {
                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_reclassFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_profileFragment)

                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this


        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

/**
 *     BottomNavigation Bar setting
 * */
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        textMessage = binding.message
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }


    /**
     *  Support Action bar
     * */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.roll_name -> {
                this.findNavController(R.id.navHostFragment)
                    .navigate(R.id.action_global_scanFragment)

                textMessage.setText(R.string.roll_name)

            }
//            R.id.log_out ->{
//                signOut()
//            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun signOut() {
        startActivity(LoginActivity.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut()
    }

}
