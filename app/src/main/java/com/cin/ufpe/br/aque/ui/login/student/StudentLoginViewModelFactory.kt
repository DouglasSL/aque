package com.cin.ufpe.br.aque.ui.login.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cin.ufpe.br.aque.data.StudentLoginDataSource
import com.cin.ufpe.br.aque.data.StudentLoginRepository

/**
 * ViewModel provider factory to instantiate StudentLoginViewModel.
 * Required given StudentLoginViewModel has a non-empty constructor
 */
class StudentLoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentLoginViewModel::class.java)) {
            return StudentLoginViewModel(
                studentLoginRepository = StudentLoginRepository(
                    dataSourceStudent = StudentLoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
