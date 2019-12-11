package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.database.ClassDB
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Class
import com.cin.ufpe.br.aque.models.ClassDescription
import com.cin.ufpe.br.aque.models.UserClass
import kotlinx.android.synthetic.main.activity_professor_add_class.*
import org.jetbrains.anko.doAsync


class ProfessorAddClassActivity : AppCompatActivity() {
    val DAYS_OF_WEEK = arrayOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professor_add_class)

        val daysOfWeekAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, DAYS_OF_WEEK)
        daysOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        first_class_day_spinner.adapter = daysOfWeekAdapter
        second_class_day_spinner.adapter = daysOfWeekAdapter

        button.setOnClickListener{
            var firstDayStartHour = first_class_day_start_hour.text.toString()
            var secondDayStartHour = second_class_day_start_hour.text.toString()
            var firstDayEndHour = first_class_day_end_hour.text.toString()
            var secondDayEndHour = second_class_day_end_hour.text.toString()

            if(add_professor_class_name.text.isEmpty() || add_classroom_name.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    "Todos os campos devem ser preenchidos",
                    Toast.LENGTH_SHORT).show()
            } else if (!isValidClassHour(firstDayStartHour)
                || !isValidClassHour(secondDayStartHour)
                || !isValidClassHour(firstDayEndHour)
                || !isValidClassHour(secondDayEndHour)) {
                Toast.makeText(applicationContext,
                    "Hora inválida",
                    Toast.LENGTH_SHORT).show()
            }

            var firstSpinner = first_class_day_spinner.selectedItem as String
            var secondSpinner = second_class_day_spinner.selectedItem as String

            if (firstSpinner.equals(secondSpinner)) {
                if (firstDayStartHour.equals(secondDayStartHour) ||
                    firstDayEndHour.equals(secondDayEndHour)) {
                    Toast.makeText(applicationContext,
                        "Dias e horas são iguais",
                        Toast.LENGTH_SHORT).show()
                } else if (firstDayStartHour.equals(firstDayEndHour) ||
                    secondDayStartHour.equals(secondDayEndHour)) {
                    Toast.makeText(applicationContext,
                        "horas iguais no mesmo dia",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                var classId = generateClassId()
                var className = add_professor_class_name.text.toString()
                var firstDay = first_class_day_spinner.selectedItemPosition + 2
                var secondDay = second_class_day_spinner.selectedItemPosition + 2


                var classDecription = ClassDescription(classId, className, firstDay,
                    secondDay, firstDayStartHour.toInt() , firstDayEndHour.toInt(),
                    secondDayStartHour.toInt(), secondDayEndHour.toInt())

                var firebaseManager = FirebaseManager()
                firebaseManager.saveClassDescription(classDecription)

                doAsync {
                    var db = ClassDB.getDatabase(applicationContext)
                    var classFirstDay = Class(0, classId, className, firstDay, firstDayStartHour.toInt(), firstDayEndHour.toInt())
                    var classSecondDay = Class(0,classId, className, secondDay, secondDayStartHour.toInt(), secondDayEndHour.toInt())

                    db.ClassDAO().add(classFirstDay)
                    db.ClassDAO().add(classSecondDay)

                    var sharedPreferences = SharedPreferencesManager(applicationContext)
                    var userClass = UserClass(sharedPreferences.getUserId(), classId)
                    firebaseManager.saveUserClass("professor_class", userClass)
                    Log.i("Professor_add_class", "Added class with id: " + classId)
                }

                startActivity(Intent(applicationContext, HomeProfessorActivity::class.java))
            }
        }
    }

    private fun isValidClassHour(hour: String) : Boolean {
        var h = hour.toInt()
        return if (h >= 0 && h <= 24) true else false
    }

    private fun generateClassId() : String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz1234567890"
        return (1..6)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
