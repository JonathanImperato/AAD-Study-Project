package com.macoev.aadstudyproject.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.macoev.aadstudyproject.R
import com.macoev.aadstudyproject.databinding.ActivityMainBinding
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import com.macoev.aadstudyproject.work.DownloadWork
import com.macoev.aadstudyproject.work.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).let {
            setContentView(it.root)
            val navController =  (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            it.toolbar.setupWithNavController(navController, appBarConfiguration)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.userDetailFragment -> {
                        it.add.hide()
                    }
                    else -> it.add.show()
                }
            }
            it.viewModel = viewModel
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            syncServer()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun syncServer() {
        RequestManager(applicationContext).run {
            periodic<DownloadWork>(interval = Duration.ofMinutes(15))
                .onError {
                    Toast.makeText(applicationContext, "onError called", Toast.LENGTH_SHORT).show()
                }.onSuccess {
                    Toast.makeText(applicationContext, "onSuccess called", Toast.LENGTH_SHORT)
                        .show()
                }
//            single<DownloadWork>(initialDelay = Duration.ofSeconds(10)).onSuccess {
//                startActivity(Intent(this@MainActivity, CalendarActivity::class.java))
//            }
        }
    }
}