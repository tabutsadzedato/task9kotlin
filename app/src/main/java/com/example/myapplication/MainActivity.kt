package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myapplication.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        initWorkManagerListener()
    }

    private fun initWorkManagerListener() {
        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData(DATABASE_USER_WORKER).observe(this) {
                if (it.isNotEmpty() && it.first().state == WorkInfo.State.SUCCEEDED) {
                    val users = App.roomDatabase.userDao().getUsers()
                    val adapter = UserAdapter(users)
                    recyclerView.adapter = adapter
                }
            }
    }

    companion object {
        const val DATABASE_USER_WORKER = "database_user_worker"
    }
}