package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private val TAG = ClassStudentsActivity::class.simpleName

    var classId: String? = null
    var className: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_students)

        classId = intent.getStringExtra("classId")
        className = intent.getStringExtra("className")
        var classDay = intent.getIntExtra("classDay", 0)
        var classMonth = intent.getIntExtra("classMonth", 0)
        var classYear = intent.getIntExtra("classYear", 0)

        class_students_list.layoutManager = LinearLayoutManager(this)
        val adapter = ClassStudentsAdapter()

        val presentStudentsId = classId + "_" + classDay + "_" + classMonth + "_" + classYear
        firebase.getPresentStudents(presentStudentsId)
            .addOnSuccessListener { document ->
                if (document == null) {
                    return@addOnSuccessListener
                }
                Log.i(TAG, "Retreived present students")
                val presentStudents = document.toObject(PresentStudents::class.java)
                doAsync {
                    uiThread {
                        adapter.studentNames = presentStudents!!.students!!
                        class_students_list.adapter = adapter
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error retreiving present students")
            }
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, ClassDetailActivity::class.java)
        intent.putExtra("classId", classId)
        intent.putExtra("className", className)
        startActivity(intent)
        super.onBackPressed()
    }
}
