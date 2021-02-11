package com.zekab.roomdatabasemigrationschema.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zekab.roomdatabasemigrationschema.datamodel.UserEntity
import com.zekab.roomdatabasemigrationschema.datamodel.UserReportCardEntity

object ObjectConverter {


    fun fromObjectToString(mObject: UserEntity):String{
        return Gson().toJson(mObject)
    }

    fun fromStringToObject(mString:String): UserEntity {
        val noteType = object :TypeToken<UserEntity>(){}.type
        return Gson().fromJson(mString,noteType)
    }

    fun fromObjectToString2(mObject: UserReportCardEntity):String{
        return Gson().toJson(mObject)
    }

    fun fromStringToObject2(mString:String): UserReportCardEntity {
        val noteType = object :TypeToken<UserReportCardEntity>(){}.type
        return Gson().fromJson(mString,noteType)
    }
}