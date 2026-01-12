package com.example.kotlin_assignment_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_assignment_2.databinding.ActivityMainBinding
import com.example.kotlin_assignment_2.models.Model

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.studentListRv.layoutManager = LinearLayoutManager(this)
        adapter = StudentsAdapter(Model.students)
        binding.studentListRv.adapter = adapter

        adapter.onRowClickListener = { position ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra(StudentDetailsActivity.EXTRA_STUDENT_ID, Model.students[position].id)
            startActivity(intent)
        }

        adapter.onCheckboxClickListener = { position, isChecked ->
            Model.students[position].isPresent = isChecked
        }

        binding.mainAddStudentBtn.setOnClickListener {
            startActivity(Intent(this, NewStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateData(Model.students)
    }
}