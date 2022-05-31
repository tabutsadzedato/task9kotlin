package com.example.myapplication.work_manager

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.myapplication.App
import com.example.myapplication.MainActivity.Companion.DATABASE_USER_WORKER
import com.example.myapplication.api.RetrofitClient
class FetchUsersWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) :
    Worker(context, workerParams) {

    private val userDao = App.roomDatabase.userDao()

    override fun doWork(): Result {
        return getUsers(1)
    }

    private fun getUsers(page: Int): Result {
        val response = RetrofitClient.reqResApi.getUsers(page).execute()
        if (response.isSuccessful) {
            val totalPages = response.body()?.totalPages
            response.body()?.data?.let { userDao.insertUsers(it) }
            if (page < (totalPages ?: 0)) {
                getUsers(page + 1)
            } else {
                startDatabaseWorker()
                return Result.success()
            }
        } else {
            Log.e("[FetchUsersWorker]", "Error while fetching users from api")
            return Result.retry()
        }
        return Result.failure()
    }

    private fun startDatabaseWorker() {
        val request = OneTimeWorkRequestBuilder<DatabaseUsersWorker>().build()
        WorkManager.getInstance(context).enqueueUniqueWork(DATABASE_USER_WORKER, ExistingWorkPolicy.REPLACE, request)
    }
}