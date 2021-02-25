package com.macoev.aadstudyproject.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.macoev.aadstudyproject.R
import com.macoev.aadstudyproject.databinding.ActivityMainBinding
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import com.macoev.aadstudyproject.work.DownloadWork
import com.macoev.aadstudyproject.work.RequestManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import java.time.Duration

@KoinApiExtension
class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
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
        }
    }
}