package com.zekab.roomdatabasemigrationschema.viewmodel

import androidx.lifecycle.*
import com.zekab.roomdatabasemigrationschema.datamodel.UserReportCardEntity
import com.zekab.roomdatabasemigrationschema.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class UserReportViewModel(private val repository: UserRepository) : ViewModel() {

    val allUserReportList: LiveData<List<UserReportCardEntity>> = repository.allUserReportList.asLiveData()

    fun insertUserReport(userReportCardEntity: UserReportCardEntity) = viewModelScope.launch {
        repository.insertUserReport(userReportCardEntity)
    }

    fun deleteUserReport(userReportCardEntity: UserReportCardEntity) = viewModelScope.launch {
        repository.deleteUserReport(userReportCardEntity)
    }

    fun updateUserReport(userReportCardEntity: UserReportCardEntity) = viewModelScope.launch {
        repository.updateUserReport(userReportCardEntity)
    }

    fun deleteAllUserReport() = viewModelScope.launch {
        repository.deleteAllUserReportList()
    }

    class UserReportViewModelFactory(private val repository: UserRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserReportViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return UserReportViewModel(repository) as T
            }
            throw IllegalStateException("Unknown ViewModel Class")
        }

    }

}