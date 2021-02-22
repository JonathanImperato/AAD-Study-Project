package com.macoev.roomsample

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Operation
import com.macoev.roomsample.data.User
import com.macoev.roomsample.viewmodel.UserViewModel
import com.macoev.roomsample.work.DownloadWork
import com.macoev.roomsample.work.RequestManager
import java.time.Duration

class MainActivity : AppCompatActivity() {

    val model: UserViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.adapter = model.adapter
        findViewById<Button>(R.id.add).setOnClickListener {
            model.insert(User.createRandom())
        }

        RequestManager(applicationContext).run {
            periodic<DownloadWork>(interval = Duration.ofMinutes(15))
                .onError {
                    Toast.makeText(this@MainActivity, "onError called", Toast.LENGTH_SHORT).show()
                }.onSuccess {
                    Toast.makeText(this@MainActivity, "onSuccess called", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun Operation.onError(error: (Operation) -> Unit): Operation {
        state.observeForever {
            when (it) {
                is Operation.State.FAILURE -> error(this)
            }
        }
        return this
    }

    private fun Operation.onSuccess(success: (Operation) -> Unit): Operation {
        state.observeForever {
            when (it) {
                is Operation.State.SUCCESS -> success(this)
            }
        }
        return this
    }
}