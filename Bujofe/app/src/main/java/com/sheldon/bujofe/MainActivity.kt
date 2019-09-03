package com.sheldon.bujofe

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sheldon.bujofe.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
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
                    navController.navigate(R.id.action_global_reclassFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    textMessage.setText(R.string.title_profile)
                    navController.navigate(R.id.action_global_profileFragment)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.roll_name -> {
                this.findNavController(R.id.navHostFragment)
                    .navigate(R.id.action_global_scanFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
