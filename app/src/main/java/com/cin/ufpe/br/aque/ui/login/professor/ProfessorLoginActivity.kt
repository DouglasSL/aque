package com.cin.ufpe.br.aque.ui.login.professor

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.actvities.HomeProfessorActivity
import com.cin.ufpe.br.aque.actvities.ProfessorRegisterActivity
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Professor
import com.cin.ufpe.br.aque.ui.login.student.afterTextChanged

class ProfessorLoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_login)

        val username = findViewById<EditText>(R.id.professor_username)
        val password = findViewById<EditText>(R.id.professor_password)
        val login = findViewById<Button>(R.id.professor_login)
        val register = findViewById<Button>(R.id.professor_register)
        val loading = findViewById<ProgressBar>(R.id.professor_loading)

        loginViewModel = ViewModelProviders.of(this,
            LoginViewModelFactory()
        )
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@ProfessorLoginActivity, Observer {
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

        loginViewModel.loginResult.observe(this@ProfessorLoginActivity, Observer {
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

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            password.text.toString(),
                            Professor()
                        )
                }
                false
            }

            login.setOnClickListener {
                firebase.getProfessor(username.text.toString())
                    .addOnSuccessListener { documentReference ->
                        val professor = documentReference.toObject(Professor::class.java) as Professor
                        Log.d("login", "Got professor ${professor.name} received from Firebase")

                        loading.visibility = View.VISIBLE
                        loginViewModel.login(password.text.toString(), professor)
                    }
                    .addOnFailureListener { e ->
                        Log.w("login", "Error receiving student", e)
                    }
            }
        }

        register.setOnClickListener {
            startActivity(Intent(applicationContext, ProfessorRegisterActivity::class.java))
        }

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
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
        sharedPreferencesManager.setUserType(false)
        startActivity(Intent(applicationContext, HomeProfessorActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
