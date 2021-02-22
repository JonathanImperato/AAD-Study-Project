package com.macoev.roomsample

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.macoev.roomsample.databinding.ActivityMainBinding
import com.macoev.roomsample.viewmodel.UserViewModel
import com.macoev.roomsample.work.DownloadWork
import com.macoev.roomsample.work.RequestManager
import java.time.Duration

class MainActivity : AppCompatActivity() {

    val model: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = model

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            syncServer()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun syncServer() {
        RequestManager(applicationContext).run {
            periodic<DownloadWork>(interval = Duration.ofMinutes(15))
                .onError {
                    Toast.makeText(this@MainActivity, "onError called", Toast.LENGTH_SHORT).show()
                }.onSuccess {
                    Toast.makeText(this@MainActivity, "onSuccess called", Toast.LENGTH_SHORT).show()
                }
        }
    }

}