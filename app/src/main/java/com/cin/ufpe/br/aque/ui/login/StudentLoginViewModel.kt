package com.cin.ufpe.br.aque.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.cin.ufpe.br.aque.data.StudentLoginRepository
import com.cin.ufpe.br.aque.data.Result

import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.models.Student

class StudentLoginViewModel(private val studentLoginRepository: StudentLoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<StudentLoginFormState>()
    val studentLoginFormState: LiveData<StudentLoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<StudentLoginResult>()
    val studentLoginResult: LiveData<StudentLoginResult> = _loginResult

    fun login(username: String, password: String, student: Student) {
        // can be launched in a separate asynchronous job
        val result = studentLoginRepository.login(username, password, student)

        if (result is Result.Success) {
            _loginResult.value =
                StudentLoginResult(success = StudentLoggedInUserView(
                    displayName = result.data.displayName,
                    email = result.data.email
                ))
        } else {
            _loginResult.value = StudentLoginResult(error = R.string.login_failed)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = StudentLoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = StudentLoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = StudentLoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
