package com.cin.ufpe.br.aque.actvities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.managers.FirebaseManager
import com.cin.ufpe.br.aque.managers.SharedPreferencesManager
import com.cin.ufpe.br.aque.models.ClassDescription
import com.cin.ufpe.br.aque.models.UserClass

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
                            "Disciplina não existe :(",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val classDescription = documentReference.toObject(ClassDescription::class.java) as ClassDescription
                        Log.d("Add_student_class", "Class ${classDescription.className} received from Firebase")

                        if(classDescription.className != null) {
                            val user = sharedPreferences.getUserId()
                            firebase.saveUserClass("student_class", UserClass(user, classCode))
                            Toast.makeText(applicationContext,
                                "Aluno registrado na disciplina!!",
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
}
