package com.example.kotlin_assignment_2.models

object Model {
    val students = mutableListOf<Student>()

    init {
        // Add some initial data for testing
        students.add(Student("John Doe", "12345", "555-1234", "123 Main St", 0, 0, false))
        students.add(Student("Jane Smith", "67890", "555-5678", "456 Oak Ave", 0, 0, true))
        students.add(Student("Peter Jones", "54321", "555-8765", "789 Pine Ln", 0, 0, false))
    }

    fun findStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(originalId: String, updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == originalId }
        if (index != -1) {
            students[index] = updatedStudent
        }
    }

    fun deleteStudent(id: String) {
        students.removeAll { it.id == id }
    }
}