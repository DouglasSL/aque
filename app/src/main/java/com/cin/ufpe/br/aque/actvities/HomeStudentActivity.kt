package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.adapters.StudentClassAdapter
import com.cin.ufpe.br.aque.dtos.StudentClassInfoDto
import kotlinx.android.synthetic.main.activity_home_student.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomeStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_student)

        classes_list.layoutManager = LinearLayoutManager(this)
        val adapter = StudentClassAdapter()

        doAsync {
//            TODO("pegar todas as infos do banco e criar a lista")
            val classInfos: List<StudentClassInfoDto> = emptyList()

            uiThread {
                adapter.classInfos = classInfos
                classes_list.adapter = adapter
            }
        }

        add_class_button.setOnClickListener {
            startActivity(Intent(applicationContext, StudentAddClassActivity::class.java))
        }
    }
}
