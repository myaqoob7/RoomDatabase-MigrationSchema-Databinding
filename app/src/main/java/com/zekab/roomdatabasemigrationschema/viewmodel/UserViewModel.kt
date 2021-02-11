package com.zekab.roomdatabasemigrationschema.viewmodel

import androidx.lifecycle.*
import com.zekab.roomdatabasemigrationschema.datamodel.UserEntity
import com.zekab.roomdatabasemigrationschema.repository.UserRepository
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val allUserList: LiveData<List<UserEntity>> = repository.allUserList.asLiveData()

    fun insertUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.insertUser(userEntity)
    }

    fun deleteUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.deleteUser(userEntity)
    }

    fun updateUser(userEntity: UserEntity) = viewModelScope.launch {
        repository.updateUser(userEntity)
    }

    fun deleteAllUser() = viewModelScope.launch {
        repository.deleteAllUserList()
    }

    class UserViewModelFactory(private val repository: UserRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(repository) as T
            }
            throw IllegalStateException("Unknown ViewModel Class")
        }

    }

}