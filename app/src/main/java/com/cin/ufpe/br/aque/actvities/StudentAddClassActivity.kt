package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.database.ClassDB
import com.cin.ufpe.br.aque.managers.AlarmManager
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Class
import com.cin.ufpe.br.aque.models.ClassDescription
import com.cin.ufpe.br.aque.models.UserClass
import org.jetbrains.anko.doAsync

class StudentAddClassActivity : AppCompatActivity() {
    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_add_class)

        val sharedPreferences = SharedPreferencesManager(applicationContext)

        val classCodeField = findViewById<EditText>(R.id.class_code_input)
        val okButton = findViewById<Button>(R.id.ok_student_class_button)

        okButton.setOnClickListener {
            val classCode = classCodeField.text.toString()
            firebase.getClassDescription(classCode)
                .addOnSuccessListener { documentReference ->
                    if(documentReference == null){
                        Toast.makeText(applicationContext,
                            "Disciplina não existe",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val classDescription = documentReference.toObject(ClassDescription::class.java) as ClassDescription
                        Log.d("Add_student_class", "Class ${classDescription.className} received from Firebase")

                        if(classDescription.className != null) {
                            val user = sharedPreferences.getUserId()

                            doAsync {
                                var db = ClassDB.getDatabase(applicationContext)
                                var classFirstDay = Class(
                                    0,
                                    classDescription.id!!,
                                    classDescription.className!!,
                                    classDescription.firstDay!!,
                                    classDescription.firstDayStartHour!!.toInt(),
                                    classDescription.firstDayEndHour!!.toInt()
                                )
                                var classSecondDay = Class(
                                    0,
                                    classDescription.id!!,
                                    classDescription.className!!,
                                    classDescription.secondDay!!,
                                    classDescription.secondDayStartHour!!.toInt(),
                                    classDescription.secondDayEndHour!!.toInt()
                                )

                                db.ClassDAO().add(classFirstDay)
                                db.ClassDAO().add(classSecondDay)

                                AlarmManager.setRoutineAlarm(applicationContext,
                                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY) ,1)
                            }
                            firebase.saveUserClass("student_class", UserClass(user, classCode))
                            Toast.makeText(applicationContext,
                                "Aluno registrado na disciplina!",
                                Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, HomeStudentActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext,
                                "Disciplina não existe :(",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Add_student_class", "Error getting class", e)
                }
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, HomeStudentActivity::class.java))
        super.onBackPressed()
    }
}
