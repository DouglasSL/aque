package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.ui.login.StudentLoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        student_button.setOnClickListener {
            startActivity(Intent(applicationContext, StudentLoginActivity::class.java))
        }

        professor_button.setOnClickListener {
            startActivity(Intent(applicationContext, HomeProfessorActivity::class.java))
        }
    }
}
