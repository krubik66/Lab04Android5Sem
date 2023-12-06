package com.example.lab04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab04.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var toolbarMain: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNav: BottomNavigationView
    lateinit var appBarConfig: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarMain = binding.toolbar
        setSupportActionBar(toolbarMain)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout=binding.drawer
        bottomNav = binding.mainNavigation


        appBarConfig =
            AppBarConfiguration(setOf(R.id.secondFragment,R.id.firstFragment,R.id.thirdFragment), drawerLayout)
        setupActionBarWithNavController(navController,appBarConfig)
        bottomNav.setupWithNavController(navController)

    }


}