package com.cin.ufpe.br.aque.managers

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.cin.ufpe.br.aque.AqueApplication
import org.jetbrains.anko.doAsync
import java.util.concurrent.TimeUnit

class WorkerManager(ctx: Context, workerParams: WorkerParameters) : Worker(ctx, workerParams) {

    companion object {
        private val TAG = WorkerManager::class.simpleName
        fun setWorkManager(ctx : Context) {
            Log.i(TAG, "Setting Work Manager")
            val work = PeriodicWorkRequestBuilder<WorkerManager>(1, TimeUnit.MINUTES)
                .build()
            val workManager = WorkManager.getInstance(ctx)
            workManager.enqueue(work)
        }

        fun cancelWorkManager(ctx : Context) {
            Log.i(TAG, "Stopping Work Manager")
            val workManager = WorkManager.getInstance(ctx)
            workManager.cancelAllWork()
        }
    }

    override fun doWork(): Result {

        Log.i(TAG, "Requesting Location Update")
        var locationManager = LocationManager()
        doAsync { locationManager.requestLocation(AqueApplication.applicationContext()) }

        return Result.success()
    }


}