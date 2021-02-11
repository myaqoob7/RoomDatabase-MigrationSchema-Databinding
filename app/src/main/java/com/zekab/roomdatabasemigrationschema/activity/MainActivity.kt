package com.zekab.roomdatabasemigrationschema.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.zekab.roomdatabasemigrationschema.R
import com.zekab.roomdatabasemigrationschema.activity.UserActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mBtnUser:Button = findViewById(R.id.btnUser)
        mBtnUser.setOnClickListener {
            startActivity(Intent(this,UserActivity::class.java))
        }

        val mBtnUserReport:Button = findViewById(R.id.btnUserReport)
        mBtnUserReport.setOnClickListener {
            startActivity(Intent(this,UserReportCardActivity::class.java))
        }
    }
}