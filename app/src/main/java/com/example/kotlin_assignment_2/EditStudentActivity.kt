package com.example.kotlin_assignment_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_assignment_2.databinding.ActivityEditStudentBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student

class EditStudentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    }

    private lateinit var binding: ActivityEditStudentBinding
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentId = intent.getStringExtra(EXTRA_STUDENT_ID)
        student = studentId?.let { Model.findStudentById(it) }

        student?.let {
            binding.editStudentNameEt.setText(it.name)
            binding.editStudentIdEt.setText(it.id)
            binding.editStudentPhoneEt.setText(it.phone)
            binding.editStudentAddressEt.setText(it.address)
            binding.editStudentCheckbox.isChecked = it.isPresent
        }

        binding.editStudentSaveBtn.setOnClickListener {
            val updatedStudent = Student(
                name = binding.editStudentNameEt.text.toString(),
                id = binding.editStudentIdEt.text.toString(),
                phone = binding.editStudentPhoneEt.text.toString(),
                address = binding.editStudentAddressEt.text.toString(),
                isPresent = binding.editStudentCheckbox.isChecked
            )
            Model.updateStudent(updatedStudent)
            finish()
        }

        binding.editStudentCancelBtn.setOnClickListener {
            finish()
        }

        binding.editStudentDeleteBtn.setOnClickListener {
            student?.id?.let { Model.deleteStudent(it) }
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}