package com.example.kotlin_assignment_2.models

object Model {
    val students = mutableListOf<Student>()

    init {
        // Add some initial data for testing
        students.add(Student("John Doe", "12345", "555-1234", "123 Main St", false))
        students.add(Student("Jane Smith", "67890", "555-5678", "456 Oak Ave", true))
        students.add(Student("Peter Jones", "54321", "555-8765", "789 Pine Ln", false))
    }

    fun findStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == updatedStudent.id }
        if (index != -1) {
            students[index] = updatedStudent
        }
    }

    fun deleteStudent(id: String) {
        students.removeAll { it.id == id }
    }
}