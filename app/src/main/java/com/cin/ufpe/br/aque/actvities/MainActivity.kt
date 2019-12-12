package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.ui.login.student.StudentLoginActivity
import com.cin.ufpe.br.aque.managers.PermissionManager
import com.cin.ufpe.br.aque.ui.login.professor.ProfessorLoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import leakcanary.AppWatcher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppWatcher.objectWatcher.watch(this)

        if (!PermissionManager.checkLocationPermission(this)) {
            PermissionManager.requestLocationPermission(this)
        }

        student_button.setOnClickListener {
            startActivity(Intent(applicationContext, StudentLoginActivity::class.java))
        }

        professor_button.setOnClickListener {
            startActivity(Intent(applicationContext, ProfessorLoginActivity::class.java))
        }
    }
}
