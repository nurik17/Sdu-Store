package com.example.sdustore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.sdustore.data.entity.Resource
import com.example.sdustore.databinding.ActivityMainBinding
import com.example.sdustore.ui.basket.BasketViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: BasketViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView: BottomNavigationView = binding.bottomNav

        bottomNavView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            when (destination.id) {
                R.id.loginFragment -> hideBottomNav()
                R.id.registerFragment -> hideBottomNav()
                R.id.historyPageFragment -> hideBottomNav()
                R.id.productDetailsFragment -> hideBottomNav()
                R.id.productsFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }

        lifecycleScope.launch {
            viewModel.cartProduct.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val count = it.data?.size ?: 0

                        val badge = bottomNavView.getOrCreateBadge(R.id.buyFragment)

                        if (count > 0) {
                            badge.apply {
                                number = count
                                backgroundColor =
                                    ContextCompat.getColor(this@MainActivity, R.color.light_yellow)
                            }
                        } else {
                            badge.clearNumber()
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.INVISIBLE
    }
}