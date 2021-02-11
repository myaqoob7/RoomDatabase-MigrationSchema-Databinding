package com.zekab.roomdatabasemigrationschema

import android.app.Application
import com.zekab.roomdatabasemigrationschema.database.UserDatabase
import com.zekab.roomdatabasemigrationschema.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainApplication:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { UserDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { UserRepository(database.userDao()) }
}