package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.managers.LocationManager
import com.cin.ufpe.br.aque.managers.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PermissionManager.checkLocationPermission(this)) {
            PermissionManager.requestLocationPermission(this)
        }

        student_button.setOnClickListener {
            startActivity(Intent(applicationContext, HomeStudentActivity::class.java))
        }

        professor_button.setOnClickListener {
            startActivity(Intent())
        }
    }
}
