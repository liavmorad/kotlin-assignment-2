package com.example.kotlin_assignment_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_assignment_2.databinding.ActivityStudentDetailsBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student

class StudentDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    }

    private lateinit var binding: ActivityStudentDetailsBinding
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentId = intent.getStringExtra(EXTRA_STUDENT_ID)
        student = studentId?.let { Model.findStudentById(it) }

        binding.detailsEditBtn.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra(EditStudentActivity.EXTRA_STUDENT_ID, student?.id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        student?.let {
            student = Model.findStudentById(it.id)
            displayStudentDetails()
        }
    }

    private fun displayStudentDetails() {
        student?.let {
            binding.detailsNameTv.text = it.name
            binding.detailsIdTv.text = it.id
            binding.detailsPhoneTv.text = it.phone
            binding.detailsAddressTv.text = it.address
            binding.detailsCheckedTv.text = if (it.isPresent) "Present" else "Not Present"
            binding.detailsStudentImage.setImageResource(it.picture)
        }
    }
}