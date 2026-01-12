package com.example.kotlin_assignment_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.kotlin_assignment_2.databinding.ActivityStudentDetailsBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student

class StudentDetailsActivity : BaseActivity() {

    companion object {
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
    }

    private lateinit var binding: ActivityStudentDetailsBinding
    private var student: Student? = null

    private val editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val studentId = result.data?.getStringExtra(EXTRA_STUDENT_ID) ?: intent.getStringExtra(EXTRA_STUDENT_ID)
            student = studentId?.let { Model.findStudentById(it) }
            displayStudentDetails()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studentId = intent.getStringExtra(EXTRA_STUDENT_ID)
        student = studentId?.let { Model.findStudentById(it) }
        displayStudentDetails()

        binding.detailsEditBtn.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra(EditStudentActivity.EXTRA_STUDENT_ID, student?.id)
            editStudentLauncher.launch(intent)
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
        } ?: finish()
    }
}