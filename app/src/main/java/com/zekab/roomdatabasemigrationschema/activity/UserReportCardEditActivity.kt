package com.zekab.roomdatabasemigrationschema.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.zekab.roomdatabasemigrationschema.R
import com.zekab.roomdatabasemigrationschema.databinding.ActivityUserReportEditBinding
import com.zekab.roomdatabasemigrationschema.datamodel.UserReportCardEntity
import com.zekab.roomdatabasemigrationschema.utils.BaseObject.USER_ADD_REQUEST_CODE
import com.zekab.roomdatabasemigrationschema.utils.BaseObject.USER_EDIT_REQUEST_CODE
import com.zekab.roomdatabasemigrationschema.utils.ObjectConverter

class UserReportCardEditActivity : BaseActivity() {

    private lateinit var binding: ActivityUserReportEditBinding
    private var requestCode = -1
    private lateinit var userReportCardEntity: UserReportCardEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_report_edit)


        requestCode = intent.getIntExtra("UserReportRequestCode", 0)
        if (requestCode == USER_EDIT_REQUEST_CODE) {
            title = "Edit User Report"
            userReportCardEntity = ObjectConverter.fromStringToObject2(intent.getStringExtra("UserReportData")!!)
            addInitialEditData()
        } else {
            title = "Add User Report"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.user_done -> {
                binding.apply {
                    if (etSubjectGrade.text.toString().isNotEmpty() &&
                        etSubjectMarks.text.toString().isNotEmpty() &&
                        etSubjectName.text.toString().isNotEmpty()
                    ) {
                        userReportSave()
                    } else {
                        showMessage("Fields are empty!")
                    }
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun userReportSave() {

        if (requestCode == USER_ADD_REQUEST_CODE) {
            binding.apply {
                userReportCardEntity = UserReportCardEntity(
                    subjectName = etSubjectName.text.toString(),
                    subjectGrade = etSubjectGrade.text.toString(),
                    subjectMarks = etSubjectMarks.text.toString().toFloat()
                )
            }
        } else {
            binding.apply {
                userReportCardEntity.subjectName = etSubjectName.text.toString()
                userReportCardEntity.subjectGrade = etSubjectGrade.text.toString()
                userReportCardEntity.subjectMarks = etSubjectMarks.text.toString().toFloat()

            }

        }

        val replyIntent = Intent()
        replyIntent.putExtra(
            "UserReportData",
            ObjectConverter.fromObjectToString2(userReportCardEntity)
        )
        setResult(Activity.RESULT_OK, replyIntent)
        finish()

    }

    private fun addInitialEditData() {
        binding.apply {
            etSubjectName.setText(userReportCardEntity.subjectName)
            etSubjectGrade.setText(userReportCardEntity.subjectGrade)
            etSubjectMarks.setText("${userReportCardEntity.subjectMarks}")
        }
    }

}