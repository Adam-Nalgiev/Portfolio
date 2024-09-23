package com.pets.insplash.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pets.insplash.R
import com.pets.insplash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

    }

    override fun onStart() {
        super.onStart()

        val navView: BottomNavigationView = binding.bottomNavigationView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_navigation)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.collectionsFragment, R.id.profileFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = destination.id != R.id.authorizationFragment
        }
    }

}