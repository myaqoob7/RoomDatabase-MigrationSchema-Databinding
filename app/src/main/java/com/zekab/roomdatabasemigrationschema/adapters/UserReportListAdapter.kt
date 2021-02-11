package com.zekab.roomdatabasemigrationschema.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zekab.roomdatabasemigrationschema.R
import com.zekab.roomdatabasemigrationschema.datamodel.UserReportCardEntity
import com.zekab.roomdatabasemigrationschema.interfaces.OnUserClickListener

class UserReportListAdapter:ListAdapter<UserReportCardEntity, UserReportListAdapter.UserViewHolder>(USER_COMPARATOR) {

    var mListener: OnUserClickListener? = null

    fun setOnItemClickListener(listener: OnUserClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_report_recycler_item, parent, false)
        return UserViewHolder(view,mListener!!)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.subjectName,current.subjectGrade,current.subjectMarks.toString())
    }


    class UserViewHolder(itemView: View, listener: OnUserClickListener) : RecyclerView.ViewHolder(itemView) {
        private val subjectName: TextView = itemView.findViewById(R.id.tvSubjectName)
        private val subjectGrade: TextView = itemView.findViewById(R.id.tvSubjectGrade)
        private val subjectMarks:TextView = itemView.findViewById(R.id.tvSubjectMarks)

        init {

            itemView.setOnClickListener{
                val mPosition = adapterPosition
                listener.onItemClick(mPosition)
            }

        }

        fun bind(mSubjectName: String,mSubjectGrade:String,mSubjectMarks:String) {
            subjectName.text = mSubjectName
            subjectGrade.text =mSubjectGrade
            subjectMarks.text = mSubjectMarks
        }

    }


    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<UserReportCardEntity>() {
            override fun areItemsTheSame(oldItem: UserReportCardEntity, newItem: UserReportCardEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserReportCardEntity, newItem: UserReportCardEntity): Boolean {
                return oldItem==newItem
            }
        }
    }

}