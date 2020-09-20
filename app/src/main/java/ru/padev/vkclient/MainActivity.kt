package ru.padev.vkclient

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    lateinit var navController: NavController
    lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        val authorized = intent.getBooleanExtra(Constants.userAuthorized, false)

        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        val graph = navController.navInflater.inflate(R.navigation.friends_nav_graph)

        navController.graph = graph
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initInsets() {

        navHost.requireView().applySystemWindowInsetsToMargin(
            left = true,
            right = true,
            top = true
        )
    }
}
