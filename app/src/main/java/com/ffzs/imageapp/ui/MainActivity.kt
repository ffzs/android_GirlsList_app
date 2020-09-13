package com.ffzs.imageapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ffzs.imageapp.R
import com.ffzs.imageapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.imageToolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
