package com.example.kotlin_assignment_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_assignment_2.databinding.StudentRowLayoutBinding
import com.example.kotlin_assignment_2.models.Student

class StudentsAdapter(private var students: List<Student>) :
    RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    var onRowClickListener: ((Int) -> Unit)? = null
    var onCheckboxClickListener: ((Int, Boolean) -> Unit)? = null

    class StudentViewHolder(val binding: StudentRowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = StudentRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.binding.studentRowNameTv.text = student.name
        holder.binding.studentRowIdTv.text = student.id
        holder.binding.studentRowCheckbox.isChecked = student.isPresent
        holder.binding.studentRowImage.setImageResource(student.picture)

        holder.itemView.setOnClickListener {
            onRowClickListener?.invoke(position)
        }
        holder.binding.studentRowCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckboxClickListener?.invoke(position, isChecked)
        }
    }

    override fun getItemCount() = students.size

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }
}