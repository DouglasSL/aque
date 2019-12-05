package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cin.ufpe.br.aque.R
import kotlinx.android.synthetic.main.activity_home_professor.*

class HomeProfessorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_professor)

        add_professor_class_button.setOnClickListener {
            startActivity(Intent(applicationContext, ProfessorAddClassActivity::class.java))
        }
    }
}
