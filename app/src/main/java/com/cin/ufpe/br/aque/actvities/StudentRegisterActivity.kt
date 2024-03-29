package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.models.Student
import com.cin.ufpe.br.aque.ui.login.student.StudentLoginActivity
import java.security.MessageDigest
import java.util.*

class StudentRegisterActivity : AppCompatActivity() {
    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)

        val firebase = FirebaseManager()

        val username = findViewById<EditText>(R.id.student_name_input)
        val password = findViewById<EditText>(R.id.student_password_input)
        val period = findViewById<EditText>(R.id.student_period_input)
        val course = findViewById<EditText>(R.id.student_course_input)
        val email = findViewById<EditText>(R.id.student_email_input)
        val cpf = findViewById<EditText>(R.id.student_cpf_input)
        val register = findViewById<Button>(R.id.student_register)

        register.setOnClickListener {
            if(!isEmailValid(email.text.toString())){
                Toast.makeText(applicationContext,
                    "Coloque um email válido",
                    Toast.LENGTH_SHORT).show()
            } else if(!isPasswordValid(password.text.toString())) {
                Toast.makeText(applicationContext,
                    "Senha deve conter mais de 5 caracteres",
                    Toast.LENGTH_SHORT).show()
            } else if(username.text.isEmpty()
                || period.text.isEmpty()
                || course.text.isEmpty()
                || cpf.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    "Todos os campos devem ser preenchidos",
                    Toast.LENGTH_SHORT).show()
            } else {
                val bytes = MessageDigest.getInstance("SHA-256").digest(password.text.toString().toByteArray())
                val hashedPassword = printHexBinary(bytes).toUpperCase()

                val newStudent = Student(UUID.randomUUID().toString(),
                    username.text.toString(),
                    email.text.toString(),
                    cpf.text.toString(),
                    period.text.toString().toInt(),
                    course.text.toString(),
                    hashedPassword
                )
                firebase.saveStudent(newStudent)
                Toast.makeText(
                    applicationContext,
                    "Aluno Registrado",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(applicationContext, StudentLoginActivity::class.java))
            }

        }
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, StudentLoginActivity::class.java))
        super.onBackPressed()
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            email.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun printHexBinary(data: ByteArray): String {
        val r = StringBuilder(data.size * 2)
        data.forEach { b ->
            val i = b.toInt()
            r.append(HEX_CHARS[i shr 4 and 0xF])
            r.append(HEX_CHARS[i and 0xF])
        }
        return r.toString()
    }
}
