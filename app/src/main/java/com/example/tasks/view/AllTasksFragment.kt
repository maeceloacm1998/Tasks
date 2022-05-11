package com.example.tasks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks.R
import com.example.tasks.databinding.FragmentAllTasksBinding
import com.example.tasks.view.controller.AllTasksController
import com.example.tasks.viewmodel.AllTasksViewModel
import kotlinx.android.synthetic.main.fragment_all_tasks.*

class AllTasksFragment : Fragment() {
    private  lateinit var binding: FragmentAllTasksBinding
    private lateinit var mViewModel: AllTasksViewModel
    private var controller = AllTasksController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View {
        mViewModel = ViewModelProvider(this).get(AllTasksViewModel::class.java)

        binding = FragmentAllTasksBinding.inflate(layoutInflater,container,false)

        initController()

        // Cria os observadores
        observe()

        // Retorna view
        return binding.root;
    }

    private fun initController() {
        binding.recyclerAllTasks.apply {
            setController(controller)
            layoutManager = LinearLayoutManager(context)
        }

        mViewModel.listAllTasks()
    }

    private fun observe() {
        mViewModel.listAllTask.observe(viewLifecycleOwner, Observer {
            controller.setData(it)
        })
    }

}
