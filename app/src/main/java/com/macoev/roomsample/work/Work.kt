package com.macoev.roomsample.work

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

    @RequiresApi(Build.VERSION_CODES.O)
    inline fun <reified T : Worker> single(
        initialDelay: Duration = Duration.ZERO,
        input: Data = Data.EMPTY,
        tag: String = ""
    ) = schedule(
        OneTimeWorkRequestBuilder<T>().setInitialDelay(initialDelay).setInputData(input).addTag(tag)
            .build()
    )

    fun schedule(request: WorkRequest) = WorkManager.getInstance(context).enqueue(request)
}