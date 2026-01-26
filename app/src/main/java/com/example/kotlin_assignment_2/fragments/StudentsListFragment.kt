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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_assignment_2.R
import com.example.kotlin_assignment_2.StudentsAdapter
import com.example.kotlin_assignment_2.databinding.FragmentStudentsListBinding
import com.example.kotlin_assignment_2.models.Model

class StudentsListFragment : Fragment() {

    private var _binding: FragmentStudentsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StudentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)  // Enable menu
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.studentListRv.layoutManager = LinearLayoutManager(requireContext())

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.studentListRv.addItemDecoration(itemDecoration)

        adapter = StudentsAdapter(Model.students)
        binding.studentListRv.adapter = adapter

        adapter.onRowClickListener = { position ->
            val bundle = Bundle().apply {
                putString("studentId", Model.students[position].id)
            }
            findNavController().navigate(R.id.action_list_to_details, bundle)
        }

        adapter.onCheckboxClickListener = { position, isChecked ->
            Model.students[position].isPresent = isChecked
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_students_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_student -> {
                findNavController().navigate(R.id.action_list_to_add)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateData(Model.students)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
