package com.example.kotlin_assignment_2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin_assignment_2.R
import com.example.kotlin_assignment_2.databinding.FragmentStudentDetailsBinding
import com.example.kotlin_assignment_2.models.Model
import java.text.SimpleDateFormat
import java.util.Locale

class StudentDetailsFragment : Fragment() {

    private var _binding: FragmentStudentDetailsBinding? = null
    private val binding get() = _binding!!
    private var student: com.example.kotlin_assignment_2.models.Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)  // Enable menu
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val studentId = arguments?.getString("studentId")
        student = studentId?.let { Model.findStudentById(it) }
        
        // Set title in action bar
        student?.let {
            (requireActivity() as? androidx.appcompat.app.AppCompatActivity)?.supportActionBar?.title = it.name
        } ?: run {
            (requireActivity() as? androidx.appcompat.app.AppCompatActivity)?.supportActionBar?.title = "Student Details"
        }
        
        displayStudentDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_student_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_edit_student -> {
                student?.id?.let { studentId ->
                    val bundle = Bundle().apply {
                        putString("studentId", studentId)
                    }
                    findNavController().navigate(R.id.action_details_to_edit, bundle)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayStudentDetails() {
        student?.let {
            binding.detailsNameTv.text = it.name
            binding.detailsIdTv.text = it.id
            binding.detailsPhoneTv.text = it.phone
            binding.detailsAddressTv.text = it.address
            
            // Display birth date
            if (it.birthDate > 0) {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.detailsBirthDateTv.text = dateFormat.format(it.birthDate)
            } else {
                binding.detailsBirthDateTv.text = "Not set"
            }
            
            // Display birth time
            if (it.birthTime > 0) {
                val hours = it.birthTime / 60
                val minutes = it.birthTime % 60
                binding.detailsBirthTimeTv.text = String.format("%02d:%02d", hours, minutes)
            } else {
                binding.detailsBirthTimeTv.text = "Not set"
            }
            
            binding.detailsCheckedTv.text = if (it.isPresent) "Present" else "Not Present"
            binding.detailsStudentImage.setImageResource(it.picture)
        } ?: run {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh student data when returning from edit
        val studentId = arguments?.getString("studentId")
        student = studentId?.let { Model.findStudentById(it) }
        
        // Update title in action bar
        student?.let {
            (requireActivity() as? androidx.appcompat.app.AppCompatActivity)?.supportActionBar?.title = it.name
        }
        
        displayStudentDetails()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
