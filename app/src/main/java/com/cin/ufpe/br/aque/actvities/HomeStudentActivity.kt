package com.cin.ufpe.br.aque.actvities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.adapters.StudentClassAdapter
import com.cin.ufpe.br.aque.dtos.StudentClassInfoDto
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.LocationManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.ClassDescription
import com.cin.ufpe.br.aque.models.UserClass
import kotlinx.android.synthetic.main.activity_home_student.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.time.DayOfWeek

class HomeStudentActivity : AppCompatActivity() {

    private val firebase = FirebaseManager()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_student)

        val sharedPreferences = SharedPreferencesManager(applicationContext)

        var locationManager = LocationManager()

        locationManager.requestLocation(this)

        classes_list.layoutManager = LinearLayoutManager(this)
        val adapter = StudentClassAdapter()

        doAsync {
            val userId = sharedPreferences.getUserId()
            firebase.getUserClasses("student_class", userId)
                .addOnSuccessListener { documents ->
                    if(documents == null || documents.isEmpty) {
                        Toast.makeText(applicationContext,
                            "Estudante não está matriculado em disciplinas",
                            Toast.LENGTH_SHORT).show()
                    }

                    val studentsClasses = mutableListOf<StudentClassInfoDto>()
                    for (document in documents) {
                        val userClass = document.toObject(UserClass::class.java)
                        val task = firebase.getClassDescription(userClass.classId!!)

                        while(!task.isComplete){}

                        val classDescription = task
                            .result!!
                            .toObject(ClassDescription::class.java) as ClassDescription

                        studentsClasses.add(
                            StudentClassInfoDto(
                                classDescription.className!!,
                                classDescription.professorName!!,
                                DayOfWeek.of(classDescription.firstDay!!),
                                DayOfWeek.of(classDescription.secondDay!!),
                                classDescription.firstDayStartHour!!,
                                classDescription.secondDayStartHour!!,
                                classDescription.firstDayEndHour!!,
                                classDescription.secondDayEndHour!!
                            )
                        )
                    }
                    uiThread {
                        adapter.classInfos = studentsClasses
                        classes_list.adapter = adapter
                    }
                }
        }

        add_class_button.setOnClickListener {
            startActivity(Intent(applicationContext, StudentAddClassActivity::class.java))
        }
    }
}
