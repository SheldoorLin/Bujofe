package com.sheldon.bujofe

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
import com.sheldon.bujofe.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var textMessage: TextView

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val navController = this.findNavController(R.id.navHostFragment)
            when (item.itemId) {
                R.id.navigation_home -> {

                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_class_schedule -> {

                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_classScheduleFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_study_room_status -> {

                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_studyRoomFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_replacement_class -> {

                    textMessage.setText(R.string.title)
                    navController.navigate(R.id.action_global_reClassFragment)
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
        }
        return super.onOptionsItemSelected(item)
    }
}
