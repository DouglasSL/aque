package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.adapters.ProfessorClassAdapter
import com.cin.ufpe.br.aque.dtos.ProfessorClassInfoDto
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.ClassDescription
import com.cin.ufpe.br.aque.models.UserClass
import kotlinx.android.synthetic.main.activity_home_professor.*
import kotlinx.android.synthetic.main.activity_home_professor.professor_classes_list
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeProfessorActivity : AppCompatActivity() {

    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_professor)

        val sharedPreferences = SharedPreferencesManager(applicationContext)

        professor_classes_list.layoutManager = LinearLayoutManager(this)
        val adapter = ProfessorClassAdapter()
        adapter.activityOwner = this

        doAsync {
            val userId = sharedPreferences.getUserId()

            firebase.getUserClasses("professor_class", userId)
                .addOnSuccessListener {documents ->
                    if(documents == null || documents.isEmpty) {
                        Toast.makeText(applicationContext,
                            "Professor não possui disciplinas",
                            Toast.LENGTH_SHORT).show()
                    }

                    val professorClasses = mutableListOf<ProfessorClassInfoDto>()
                    for(document in documents) {
                        val userClass = document.toObject(UserClass::class.java)
                        val task = firebase.getClassDescription(userClass.classId!!)

                        while(!task.isComplete){}

                        val classDescription = task
                            .result!!
                            .toObject(ClassDescription::class.java) as ClassDescription

                        professorClasses.add(
                            ProfessorClassInfoDto(
                                classDescription.className!!,
                                classDescription.id!!
                            )
                        )
                    }

                    uiThread {
                        adapter.classInfos = professorClasses
                        professor_classes_list.adapter = adapter
                    }
                }

        }

        add_professor_class_button.setOnClickListener {
            startActivity(Intent(applicationContext, ProfessorAddClassActivity::class.java))
        }
    }
}
