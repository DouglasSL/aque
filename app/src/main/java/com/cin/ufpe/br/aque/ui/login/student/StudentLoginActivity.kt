package com.cin.ufpe.br.aque.ui.login.student

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.actvities.HomeStudentActivity
import com.cin.ufpe.br.aque.actvities.StudentRegisterActivity
import com.cin.ufpe.br.aque.database.ClassDB
import com.cin.ufpe.br.aque.managers.AlarmManager
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Class
import com.cin.ufpe.br.aque.models.Student
import com.cin.ufpe.br.aque.utils.Utils
import org.jetbrains.anko.doAsync

class StudentLoginActivity : AppCompatActivity() {

    private lateinit var studentLoginViewModel: StudentLoginViewModel
    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)

        studentLoginViewModel = ViewModelProviders.of(this,
            StudentLoginViewModelFactory()
        )
            .get(StudentLoginViewModel::class.java)

        studentLoginViewModel.studentLoginFormState.observe(this@StudentLoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        studentLoginViewModel.studentLoginResult.observe(this@StudentLoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        register.setOnClickListener {
            startActivity(Intent(applicationContext, StudentRegisterActivity::class.java))
        }

        username.afterTextChanged {
            studentLoginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                studentLoginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        studentLoginViewModel.login(
                            password.text.toString(),
                            Student()
                        )
                }
                false
            }

            login.setOnClickListener {
                firebase.getStudent(username.text.toString())
                    .addOnSuccessListener { documentReference ->
                        val student = documentReference.toObject(Student::class.java) as Student
                        Log.d("login", "Got student ${student.name} received from Firebase")

                        loading.visibility = View.VISIBLE
                        studentLoginViewModel.login(password.text.toString(), student)
                    }
                    .addOnFailureListener { e ->
                        Log.w("login", "Error receiving student", e)
                    }

            }
        }
    }

    private fun updateUiWithUser(model: StudentLoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

        val sharedPreferencesManager = SharedPreferencesManager(applicationContext)
        sharedPreferencesManager.setUserId(model.email)
        sharedPreferencesManager.setUserType(true)
        AlarmManager.setRoutineAlarm(applicationContext)
        Utils.checkForClass(applicationContext, "student_class", model.email)
        startActivity(Intent(applicationContext, HomeStudentActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
