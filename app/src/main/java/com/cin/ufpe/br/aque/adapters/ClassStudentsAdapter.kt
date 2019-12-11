package com.cin.ufpe.br.aque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cin.ufpe.br.aque.R
import kotlinx.android.synthetic.main.class_students_item.view.*

class ClassStudentsAdapter : RecyclerView.Adapter<ClassStudentsAdapter.ViewHolder>() {

    var studentNames: List<String> = emptyList()

    override fun getItemCount(): Int {
        return studentNames.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_students_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studentName = studentNames[position]

        holder.studentName.text = studentName
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val studentName = item.class_student_name
    }
}