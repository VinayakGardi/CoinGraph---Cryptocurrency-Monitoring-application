package com.vinayakgardi.coingraph.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vinayakgardi.coingraph.R
import com.vinayakgardi.coingraph.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpSmoothBottomBar()



    }

    private fun setUpSmoothBottomBar() {
        // Set up Navigation Controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        // Set up SmoothBottomBar navigation with NavController
        binding.bottomBarMenu.onItemSelected = { index ->
            when (index) {
                0 -> navController.navigate(R.id.homeFragment)
                1 -> navController.navigate(R.id.statsFragment)
                2 -> navController.navigate(R.id.savedFragment)
            }
        }
    }
}