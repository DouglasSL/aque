package com.cin.ufpe.br.aque.actvities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.adapters.ClassStudentsAdapter
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.models.PresentStudents
import kotlinx.android.synthetic.main.activity_class_students.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ClassStudentsActivity : AppCompatActivity() {
    private val firebase = FirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_students)

        val classId = intent.getStringExtra("classId")
        val classDay = intent.getStringExtra("classDay")
        val classMonth = intent.getStringExtra("classMonth")
        val classYear = intent.getStringExtra("classYear")

        class_students_list.layoutManager = LinearLayoutManager(this)
        val adapter = ClassStudentsAdapter()

        val presentStudentsId = classId + "_" + classDay + "_" + classMonth + "_" + classYear
        val task = firebase.getPresentStudents(presentStudentsId)

        while(!task.isComplete){}

        val presentStudents = task
            .result!!
            .toObject(PresentStudents::class.java) as PresentStudents

        doAsync {
            uiThread {
                adapter.studentNames = presentStudents.students
                class_students_list.adapter = adapter
            }
        }
    }
}
