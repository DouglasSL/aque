package com.cin.ufpe.br.aque.data

import com.cin.ufpe.br.aque.data.model.StudentLoggedInUser
import com.cin.ufpe.br.aque.models.Student

/**
 * Class that requests authentication and userStudent information from the remote data source and
 * maintains an in-memory cache of login status and userStudent credentials information.
 */

class StudentLoginRepository(val dataSourceStudent: StudentLoginDataSource) {

    // in-memory cache of the loggedInUser object
    var userStudent: StudentLoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = userStudent != null

    init {
        // If userStudent credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        userStudent = null
    }

    fun logout() {
        userStudent = null
        dataSourceStudent.logout()
    }

    fun login(username: String, password: String, student: Student): Result<StudentLoggedInUser> {
        // handle login
        val result = dataSourceStudent.login(username, password, student)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(studentLoggedInUser: StudentLoggedInUser) {
        this.userStudent = studentLoggedInUser

        // If userStudent credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
