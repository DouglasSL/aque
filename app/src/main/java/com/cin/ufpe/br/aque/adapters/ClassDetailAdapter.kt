package com.cin.ufpe.br.aque.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cin.ufpe.br.aque.R
import com.cin.ufpe.br.aque.actvities.ClassDetailActivity
import com.cin.ufpe.br.aque.actvities.ClassStudentsActivity
import com.cin.ufpe.br.aque.dtos.DetailClassInfoDto
import kotlinx.android.synthetic.main.detail_class_item.view.*

class ClassDetailAdapter : RecyclerView.Adapter<ClassDetailAdapter.ViewHolder>() {

    var detailClassInfos: List<DetailClassInfoDto> = emptyList()
    var activityOwner: ClassDetailActivity? = null

    override fun getItemCount(): Int {
        return detailClassInfos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_class_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detailClassInfo = detailClassInfos[position]
        holder.classDate.text = detailClassInfo.classDay.toString() + "/" + detailClassInfo.classMonth + "/" + detailClassInfo.classYear
        holder.classStudentsNumber.text = detailClassInfo.studentsNumber.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(activityOwner!!.applicationContext, ClassStudentsActivity::class.java)
            intent.putExtra("classId", detailClassInfo.classId)
            intent.putExtra("className", detailClassInfo.className)
            intent.putExtra("classDay", detailClassInfo.classDay)
            intent.putExtra("classMonth", detailClassInfo.classMonth)
            intent.putExtra("classYear", detailClassInfo.classYear)

            activityOwner!!.startActivity(intent)
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val classDate = item.detail_class_date
        val classStudentsNumber = item.detail_class_students_number
    }
}