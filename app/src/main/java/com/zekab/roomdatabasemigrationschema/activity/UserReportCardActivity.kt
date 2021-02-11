package com.zekab.roomdatabasemigrationschema.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zekab.roomdatabasemigrationschema.MainApplication
import com.zekab.roomdatabasemigrationschema.R
import com.zekab.roomdatabasemigrationschema.adapters.UserReportListAdapter
import com.zekab.roomdatabasemigrationschema.databinding.ActivityUserReportBinding
import com.zekab.roomdatabasemigrationschema.datamodel.UserReportCardEntity
import com.zekab.roomdatabasemigrationschema.interfaces.OnUserClickListener
import com.zekab.roomdatabasemigrationschema.utils.BaseObject.USER_ADD_REQUEST_CODE
import com.zekab.roomdatabasemigrationschema.utils.BaseObject.USER_EDIT_REQUEST_CODE
import com.zekab.roomdatabasemigrationschema.utils.ObjectConverter
import com.zekab.roomdatabasemigrationschema.viewmodel.UserReportViewModel

class UserReportCardActivity : BaseActivity() {
    private lateinit var binding: ActivityUserReportBinding

    private val userViewModel: UserReportViewModel by viewModels {
        UserReportViewModel.UserReportViewModelFactory((application as MainApplication).repository)
    }

    private lateinit var mAdapter: UserReportListAdapter
    private lateinit var userReportCardEntity: UserReportCardEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_report)
        title = "User Report Card"


        iniViews()
        createRecyclerView()

        userViewModel.allUserReportList.observe(owner = this) { userReportList ->

            userReportList.let {
                mAdapter.submitList(it)
            }

        }
    }

    private fun iniViews(){

        binding.addUserReport.setOnClickListener {
            val intent=Intent(this, UserReportCardEditActivity::class.java)
            intent.putExtra("UserReportRequestCode", USER_ADD_REQUEST_CODE)
            startActivityForResult(intent, USER_ADD_REQUEST_CODE)
        }

    }

    private fun createRecyclerView() {
        mAdapter = UserReportListAdapter()
        binding.userReportRecyclerview.adapter = mAdapter
        binding.userReportRecyclerview.layoutManager = LinearLayoutManager(this)

        mAdapter.setOnItemClickListener(object : OnUserClickListener {
            override fun onItemClick(position: Int) {
                userReportCardEntity = mAdapter.currentList[position]

                val intent = Intent(this@UserReportCardActivity, UserReportCardEditActivity::class.java)
                intent.putExtra("UserReportRequestCode", USER_EDIT_REQUEST_CODE)
                intent.putExtra("UserReportData", ObjectConverter.fromObjectToString2(userReportCardEntity))
                startActivityForResult(intent, USER_EDIT_REQUEST_CODE)

            }


        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userReportCardEntity = mAdapter.currentList[viewHolder.adapterPosition]
                userViewModel.deleteUserReport(userReportCardEntity)
                showMessage("Item Deleted!")
            }
        }).attachToRecyclerView(binding.userReportRecyclerview)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if ( resultCode == Activity.RESULT_OK) {
            if (requestCode== USER_ADD_REQUEST_CODE){
                intentData?.getStringExtra("UserReportData")?.let { reply ->
                    val mUserReport = ObjectConverter.fromStringToObject2(reply)
                    userViewModel.insertUserReport(mUserReport)
                }

            }

            if (requestCode== USER_EDIT_REQUEST_CODE){
                intentData?.getStringExtra("UserReportData")?.let { reply ->
                    val mUserReport = ObjectConverter.fromStringToObject2(reply)
                    userViewModel.updateUserReport(mUserReport)
                }

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.user_remove -> {
                clearData()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private  fun clearData(){
        userViewModel.deleteAllUserReport()
    }
}