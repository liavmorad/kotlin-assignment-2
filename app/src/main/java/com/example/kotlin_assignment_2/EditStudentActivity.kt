package com.example.kotlin_assignment_2

import android.content.Intent
import android.os.Bundle
import com.example.kotlin_assignment_2.databinding.ActivityEditStudentBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student

class EditStudentActivity : BaseActivity() {

    companion object {
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    }

    private lateinit var binding: ActivityEditStudentBinding
    private var student: Student? = null
    private var originalStudentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalStudentId = intent.getStringExtra(EXTRA_STUDENT_ID)
        student = originalStudentId?.let { Model.findStudentById(it) }

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
                birthDate = 0,
                birthTime = 0,
                isPresent = binding.editStudentCheckbox.isChecked
            )
            originalStudentId?.let { Model.updateStudent(it, updatedStudent) }

            val resultIntent = Intent()
            resultIntent.putExtra(StudentDetailsActivity.EXTRA_STUDENT_ID, updatedStudent.id)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.editStudentCancelBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.editStudentDeleteBtn.setOnClickListener {
            student?.id?.let { Model.deleteStudent(it) }
            setResult(RESULT_OK)
            finish()
        }
    }
}