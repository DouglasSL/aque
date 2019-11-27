package com.cin.ufpe.br.aque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.dtos.StudentClassInfoDto
import kotlinx.android.synthetic.main.student_class_item.view.*

class StudentClassAdapter : RecyclerView.Adapter<StudentClassAdapter.ViewHolder>() {

    var classInfos: List<StudentClassInfoDto> = emptyList()

    override fun getItemCount(): Int {
        return classInfos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_class_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented")
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val className = item.student_class_name
        val classProfessorName = item.student_class_professor_name
        val firstSchedule = item.student_class_first_schedule
        val secondeSchedule = item.student_class_second_schedule
    }
}