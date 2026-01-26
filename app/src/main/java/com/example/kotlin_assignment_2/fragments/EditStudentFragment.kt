package com.example.kotlin_assignment_2.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.kotlin_assignment_2.databinding.FragmentEditStudentBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditStudentFragment : Fragment() {

    private var _binding: FragmentEditStudentBinding? = null
    private val binding get() = _binding!!
    private var student: com.example.kotlin_assignment_2.models.Student? = null
    private var originalStudentId: String? = null
    private val calendar = Calendar.getInstance()
    private var birthDateTimestamp: Long = 0
    private var birthTimeMinutes: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        originalStudentId = arguments?.getString("studentId")
        student = originalStudentId?.let { Model.findStudentById(it) }

        student?.let {
            binding.editStudentNameEt.setText(it.name)
            binding.editStudentIdEt.setText(it.id)
            binding.editStudentPhoneEt.setText(it.phone)
            binding.editStudentAddressEt.setText(it.address)
            binding.editStudentCheckbox.isChecked = it.isPresent
            
            if (it.birthDate > 0) {
                birthDateTimestamp = it.birthDate
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.editStudentBirthDateEt.setText(dateFormat.format(it.birthDate))
            }
            
            if (it.birthTime > 0) {
                birthTimeMinutes = it.birthTime
                val hours = it.birthTime / 60
                val minutes = it.birthTime % 60
                binding.editStudentBirthTimeEt.setText(String.format("%02d:%02d", hours, minutes))
            }
        }

        setupDatePicker()
        setupTimePicker()
        setupInputFields()
        setupButtons()
    }

    private fun setupDatePicker() {
        binding.editStudentBirthDateEt.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    birthDateTimestamp = calendar.timeInMillis
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.editStudentBirthDateEt.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun setupTimePicker() {
        binding.editStudentBirthTimeEt.setOnClickListener {
            val timePicker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    birthTimeMinutes = hourOfDay * 60 + minute
                    binding.editStudentBirthTimeEt.setText(
                        String.format("%02d:%02d", hourOfDay, minute)
                    )
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePicker.show()
        }
    }

    private fun setupInputFields() {
        binding.editStudentNameEt.setOnClickListener { showInputDialog("Name", binding.editStudentNameEt) }
        binding.editStudentIdEt.setOnClickListener { showInputDialog("ID", binding.editStudentIdEt) }
        binding.editStudentPhoneEt.setOnClickListener { showInputDialog("Phone", binding.editStudentPhoneEt) }
        binding.editStudentAddressEt.setOnClickListener { showInputDialog("Address", binding.editStudentAddressEt) }
    }

    private fun showInputDialog(hint: String, editText: EditText) {
        val input = EditText(requireContext())
        input.hint = hint
        input.setText(editText.text.toString())

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Enter $hint")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                editText.setText(input.text.toString())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupButtons() {
        binding.editStudentSaveBtn.setOnClickListener {
            val updatedStudent = Student(
                name = binding.editStudentNameEt.text.toString(),
                id = binding.editStudentIdEt.text.toString(),
                phone = binding.editStudentPhoneEt.text.toString(),
                address = binding.editStudentAddressEt.text.toString(),
                birthDate = birthDateTimestamp,
                birthTime = birthTimeMinutes,
                isPresent = binding.editStudentCheckbox.isChecked
            )
            originalStudentId?.let { Model.updateStudent(it, updatedStudent) }
            
            showSuccessDialog("Student updated successfully!")
        }

        binding.editStudentCancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editStudentDeleteBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Delete") { _, _ ->
                    student?.id?.let { Model.deleteStudent(it) }
                    showSuccessDialog("Student deleted successfully!")
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun showSuccessDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Success")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
