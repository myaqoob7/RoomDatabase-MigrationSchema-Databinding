package com.zekab.roomdatabasemigrationschema.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_report_card_table")
data class UserReportCardEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "subjectName") var subjectName: String,
    @ColumnInfo(name = "subjectGrade") var subjectGrade: String,
    @ColumnInfo(name = "subjectMarks") var subjectMarks:Float
)
