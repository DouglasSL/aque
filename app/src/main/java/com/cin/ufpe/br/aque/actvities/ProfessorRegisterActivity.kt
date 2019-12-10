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
import com.cin.ufpe.br.aque.models.Professor
import com.cin.ufpe.br.aque.ui.login.professor.ProfessorLoginActivity
import java.security.MessageDigest

class ProfessorRegisterActivity : AppCompatActivity() {
    private val HEX_CHARS = "0123456789ABCDEF".toCharArray()
    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_register)

        val username = findViewById<EditText>(R.id.professor_name_input)
        val password = findViewById<EditText>(R.id.professor_password_input)
        val universityName = findViewById<EditText>(R.id.professor_university_name_input)
        val email = findViewById<EditText>(R.id.professor_email_input)
        val cpf = findViewById<EditText>(R.id.professor_cpf_input)
        val register = findViewById<Button>(R.id.professor_register_button)

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
                || universityName.text.isEmpty()
                || cpf.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    "Todos os campos devem ser preenchidos",
                    Toast.LENGTH_SHORT).show()
            } else {
                val bytes = MessageDigest.getInstance("SHA-256").digest(password.text.toString().toByteArray())
                val hashedPassword = printHexBinary(bytes).toUpperCase()

                val newProfessor = Professor(
                    email.text.toString(),
                    username.text.toString(),
                    cpf.text.toString(),
                    universityName.text.toString(),
                    hashedPassword
                )
                firebase.saveProfessor(newProfessor)
                Toast.makeText(
                    applicationContext,
                    "Professor Registrado",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(applicationContext, ProfessorLoginActivity::class.java))
            }

        }
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
