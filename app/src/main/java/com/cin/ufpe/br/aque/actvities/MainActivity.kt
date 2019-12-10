package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cin.ufpe.br.aque.AqueApplication
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.ui.login.StudentLoginActivity
import com.cin.ufpe.br.aque.managers.AlarmManager
import com.cin.ufpe.br.aque.managers.PermissionManager
import com.cin.ufpe.br.aque.managers.WorkerManager
import com.cin.ufpe.br.aque.ui.login.ProfessorLoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
