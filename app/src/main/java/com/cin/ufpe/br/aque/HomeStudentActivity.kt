package com.cin.ufpe.br.aque

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class HomeStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_student)

        student_button.setOnClickListener {
            startActivity(Intent(applicationContext, HomeStudentActivity::class.java))
        }

        professor_button.setOnClickListener {
            startActivity(Intent())
        }
    }
}
