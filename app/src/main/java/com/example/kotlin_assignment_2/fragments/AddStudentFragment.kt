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
import com.example.kotlin_assignment_2.R
import com.example.kotlin_assignment_2.databinding.FragmentAddStudentBinding
import com.example.kotlin_assignment_2.models.Model
import com.example.kotlin_assignment_2.models.Student
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    private var birthDateTimestamp: Long = 0
    private var birthTimeMinutes: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatePicker()
        setupTimePicker()
        setupInputFields()
        setupButtons()
    }

    private fun setupDatePicker() {
        binding.newStudentBirthDateEt.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    birthDateTimestamp = calendar.timeInMillis
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.newStudentBirthDateEt.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun setupTimePicker() {
        binding.newStudentBirthTimeEt.setOnClickListener {
            val timePicker = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    birthTimeMinutes = hourOfDay * 60 + minute
                    binding.newStudentBirthTimeEt.setText(
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
        binding.newStudentNameEt.setOnClickListener { showInputDialog("Name", binding.newStudentNameEt) }
        binding.newStudentIdEt.setOnClickListener { showInputDialog("ID", binding.newStudentIdEt) }
        binding.newStudentPhoneEt.setOnClickListener { showInputDialog("Phone", binding.newStudentPhoneEt) }
        binding.newStudentAddressEt.setOnClickListener { showInputDialog("Address", binding.newStudentAddressEt) }
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
        binding.newStudentSaveBtn.setOnClickListener {
            val name = binding.newStudentNameEt.text.toString()
            val id = binding.newStudentIdEt.text.toString()
            val phone = binding.newStudentPhoneEt.text.toString()
            val address = binding.newStudentAddressEt.text.toString()
            val isPresent = binding.newStudentCheckbox.isChecked

            if (name.isNotBlank() && id.isNotBlank()) {
                val newStudent = Student(
                    name = name,
                    id = id,
                    phone = phone,
                    address = address,
                    birthDate = birthDateTimestamp,
                    birthTime = birthTimeMinutes,
                    isPresent = isPresent
                )
                Model.addStudent(newStudent)
                
                showSuccessDialog("Student added successfully!")
            }
        }

        binding.newStudentCancelBtn.setOnClickListener {
            findNavController().popBackStack()
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
