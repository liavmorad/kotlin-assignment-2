package com.example.kotlin_assignment_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_assignment_2.databinding.ActivityNewStudentBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student

class NewStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newStudentSaveBtn.setOnClickListener {
            val name = binding.newStudentNameEt.text.toString()
            val id = binding.newStudentIdEt.text.toString()
            val phone = binding.newStudentPhoneEt.text.toString()
            val address = binding.newStudentAddressEt.text.toString()
            val isPresent = binding.newStudentCheckbox.isChecked

            if (name.isNotBlank() && id.isNotBlank()) {
                val newStudent = Student(name, id, phone, address, isPresent)
                Model.addStudent(newStudent)
                finish()
            }
        }

        binding.newStudentCancelBtn.setOnClickListener {
            finish()
        }
    }
}