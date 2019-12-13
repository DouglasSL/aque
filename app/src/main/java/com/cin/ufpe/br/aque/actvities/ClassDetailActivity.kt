package com.cin.ufpe.br.aque.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.adapters.ClassDetailAdapter
import com.cin.ufpe.br.aque.dtos.DetailClassInfoDto
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.Date
import com.cin.ufpe.br.aque.models.PresentStudents
import kotlinx.android.synthetic.main.activity_class_detail.*
import leakcanary.AppWatcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ClassDetailActivity : AppCompatActivity() {
    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        AppWatcher.objectWatcher.watch(this)

        val className = intent.getStringExtra("className")
        val classId = intent.getStringExtra("classId")

        class_name_professor_title.text = className

        class_dates_list.layoutManager = LinearLayoutManager(this)
        val adapter = ClassDetailAdapter()
        adapter.activityOwner = this


        doAsync {
            firebase.getClassDates(classId)
                .addOnSuccessListener {documents ->
                    if(documents == null || documents.isEmpty) {
                        Toast.makeText(applicationContext,
                            "Não há dias que houve aula",
                            Toast.LENGTH_SHORT).show()
                    }

                    val detailClassInfosDto = mutableListOf<DetailClassInfoDto>()
                    for(document in documents) {
                        val dateClass = document.toObject(Date::class.java)
                        val day = dateClass.day
                        val month = dateClass.month
                        val year = dateClass.year
                        val presentStudentsId = classId + "_" + day + "_" + month + "_" + year
                        val task = firebase.getPresentStudents(presentStudentsId)

                        while(!task.isComplete){}

                        val presentStudents = task
                            .result!!
                            .toObject(PresentStudents::class.java) as PresentStudents

                        detailClassInfosDto.add(
                            DetailClassInfoDto(
                                className,
                                classId,
                                day,
                                month,
                                year,
                                presentStudents.students!!.size
                            )
                        )
                    }

                    uiThread {
                        adapter.detailClassInfos = detailClassInfosDto
                        class_dates_list.adapter = adapter
                    }
                }

        }
    }
}
