package com.example.myapplication

import android.app.Application
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.work_manager.FetchUsersWorker

class App : Application() {

    private val fetchUsersWorkRequest = OneTimeWorkRequestBuilder<FetchUsersWorker>().build()

    override fun onCreate() {
        super.onCreate()
        roomDatabase = AppDatabase.initialize(applicationContext)
        RetrofitClient.initClient()

        WorkManager.getInstance(applicationContext).enqueue(fetchUsersWorkRequest)
    }

    companion object {
        lateinit var roomDatabase: AppDatabase
            private set
    }
}