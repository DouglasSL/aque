package com.cin.ufpe.br.aque.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.dtos.StudentClassInfoDto
import com.cin.ufpe.br.aque.utils.DayOfWeekUtils
import kotlinx.android.synthetic.main.student_class_item.view.*

class StudentClassAdapter : RecyclerView.Adapter<StudentClassAdapter.ViewHolder>() {

    var classInfos: List<StudentClassInfoDto> = emptyList()

    override fun getItemCount(): Int {
        return classInfos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_class_item, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val classItem = classInfos[position]

        holder.className.text = classItem.className
        holder.classProfessorName.text = classItem.professorName
        holder.firstSchedule.text = DayOfWeekUtils.getPortugueseDayOfWeek(classItem.firstDayOfWeek) + " - " + classItem.firstStartTime + " às " + classItem.firstEndTime
        holder.secondeSchedule.text = DayOfWeekUtils.getPortugueseDayOfWeek(classItem.secondDayOfWeek) + " - " + classItem.secondStartTime + " às " + classItem.secondEndTime
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val className = item.student_class_name
        val classProfessorName = item.student_class_professor_name
        val firstSchedule = item.student_class_first_schedule
        val secondeSchedule = item.student_class_second_schedule
    }
}