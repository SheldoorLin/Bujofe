package com.sheldon.bujofe

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val navController = this.findNavController(R.id.navHostFragment)
            when (item.itemId) {
                R.id.navigation_home -> {
                    textMessage.setText(R.string.title_home)
                    navController.navigate(R.id.action_global_homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_calendar -> {
                    textMessage.setText(R.string.title_calendar)
                    navController.navigate(R.id.action_global_calendarFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_shelf_read_room_status -> {
                    textMessage.setText(R.string.title_shelf_read_room_status)
                    navController.navigate(R.id.action_global_studyRoomFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_replacement_lesson_list -> {
                    textMessage.setText(R.string.title_replacement_lesson_list)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    textMessage.setText(R.string.title_profile)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
