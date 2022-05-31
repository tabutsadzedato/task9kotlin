package com.example.myapplication.work_manager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.App

class DatabaseUsersWorker(
    context: Context,
    workerParams: WorkerParameters,
) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        App.roomDatabase.userDao().removeExcessUsers()
        return Result.success()
    }
}