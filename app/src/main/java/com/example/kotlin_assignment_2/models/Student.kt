package com.example.kotlin_assignment_2.models

data class Student(
    var name: String,
    var id: String,
    var phone: String,
    var address: String,
    var birthDate: Long = 0,
    var birthTime: Int = 0,
    var isPresent: Boolean = false,
    val picture: Int = com.example.kotlin_assignment_2.R.drawable.ic_student_placeholder
)
