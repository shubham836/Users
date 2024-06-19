package com.shubh.users.ui

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.elevation.SurfaceColors
import com.shubh.users.App
import com.shubh.users.R
import com.shubh.users.UserRepository
import com.shubh.users.databinding.ActivityMainBinding
import com.shubh.users.ui.viewmodel.UserViewModel
import com.shubh.users.ui.viewmodel.UserViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel:UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val color = SurfaceColors.SURFACE_0.getColor(this)
        // Set color of system statusBar same as ActionBar
        window.statusBarColor = color
        // Set color of system navigationBar same as BottomNavigationView
        window.navigationBarColor =
            SurfaceColors.SURFACE_1.getColor(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        val userDB = (application as App).userDB
        val userViewModelFactory = UserViewModelProviderFactory(UserRepository(userDB))
        userViewModel = ViewModelProvider(this,userViewModelFactory).get(UserViewModel::class.java)

        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                if (destination.id == R.id.userDetailFragment)
                    binding.bottomNav.visibility = View.GONE
                else
                    binding.bottomNav.visibility = View.VISIBLE
            }

        })
    }
}