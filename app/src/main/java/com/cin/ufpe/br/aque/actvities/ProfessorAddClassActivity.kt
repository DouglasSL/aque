package com.cin.ufpe.br.aque.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.cin.ufpe.br.aque.R
import kotlinx.android.synthetic.main.activity_professor_add_class.*


class ProfessorAddClassActivity : AppCompatActivity() {
    val DAYS_OF_WEEK = arrayOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_add_class)

        val daysOfWeekAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, DAYS_OF_WEEK)
        daysOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        first_class_day_spinner.adapter = daysOfWeekAdapter
        second_class_day_spinner.adapter = daysOfWeekAdapter
    }
}
