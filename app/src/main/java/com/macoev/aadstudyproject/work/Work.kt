package com.macoev.aadstudyproject.work

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import java.time.Duration


class DownloadWork(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {

    private val maxRetryCount = 3
    private var retryCount = 0

    override fun doWork(): Result {
        val doAction = Any() //perform network call
        // Sleep on a background thread.
        Thread.sleep(1000)
        val res = Result.success()
        return if (retryCount++ < maxRetryCount && res != Result.success()) Result.retry()
        else res
    }
}

class RequestManager(private val context: Context) {

    /**
     * Schedule a periodic work
     * @param interval min duration is 15 minutes. [PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS]
     * @param tag optional tag
     */
    @RequiresApi(Build.VERSION_CODES.O)
    inline fun <reified T : Worker> periodic(interval: Duration = Duration.ZERO, tag: String = "") =
        schedule(PeriodicWorkRequestBuilder<T>(interval).addTag(tag).build())

    /**
     * Send a one time work
     * @param initialDelay optional request delay
     * @param tag optional tag
     */
    @RequiresApi(Build.VERSION_CODES.O)
    inline fun <reified T : Worker> single(
        initialDelay: Duration = Duration.ZERO,
        tag: String = ""
    ) = schedule(OneTimeWorkRequestBuilder<T>().setInitialDelay(initialDelay).addTag(tag).build())

    fun schedule(request: WorkRequest) = WorkManager.getInstance(context).enqueue(request)

    fun Operation.onError(error: (Operation) -> Unit): Operation {
        state.observeForever {
            when (it) {
                is Operation.State.FAILURE -> error(this)
            }
        }
        return this
    }

    fun Operation.onSuccess(success: (Operation) -> Unit): Operation {
        state.observeForever {
            when (it) {
                is Operation.State.SUCCESS -> success(this)
            }
        }
        return this
    }
}

