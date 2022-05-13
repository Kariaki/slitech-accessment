package com.thirdwinter.slitechassessment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.thirdwinter.slitechassessment.db.architecture.dao.api.FootballApi
import com.thirdwinter.slitechassessment.db.architecture.viewmodels.FootballViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import slitechassessment.R
import slitechassessment.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val viewModel: FootballViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    lateinit var configuration: AppBarConfiguration

    private lateinit var navController: NavController

    @Inject
    lateinit var api: FootballApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hostFragment = supportFragmentManager.findFragmentById(R.id.navController)
        navController = hostFragment?.findNavController()!!
        configuration = AppBarConfiguration(
            setOf(R.id.competitionsFragment)
        )
        setSupportActionBar(binding.toolbar)


        setupActionBarWithNavController(navController, configuration)


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            finish()
        }
    }
}