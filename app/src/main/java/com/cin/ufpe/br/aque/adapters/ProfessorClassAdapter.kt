package com.cin.ufpe.br.aque.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.actvities.ClassDetailActivity
import com.cin.ufpe.br.aque.actvities.HomeProfessorActivity
import com.cin.ufpe.br.aque.dtos.ProfessorClassInfoDto
import com.cin.ufpe.br.aque.dtos.StudentClassInfoDto
import kotlinx.android.synthetic.main.professor_class_item.view.*
import kotlinx.android.synthetic.main.student_class_item.view.*

class ProfessorClassAdapter : RecyclerView.Adapter<ProfessorClassAdapter.ViewHolder>() {

    var classInfos: List<ProfessorClassInfoDto> = emptyList()
    var activityOwner: HomeProfessorActivity? = null

    override fun getItemCount(): Int {
        return classInfos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.professor_class_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val classInfo = classInfos[position]
        holder.className.text = classInfo.className
        holder.classId.text = classInfo.classId

        holder.itemView.setOnClickListener {
            val intent = Intent(activityOwner!!.applicationContext, ClassDetailActivity::class.java)
            intent.putExtra("classId", classInfo.classId)
            intent.putExtra("className", classInfo.className)

            activityOwner!!.startActivity(intent)
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val className = item.professor_class_name
        val classId = item.professor_class_id
    }
}