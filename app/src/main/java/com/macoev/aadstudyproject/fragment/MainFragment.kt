package com.macoev.aadstudyproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.macoev.aadstudyproject.adapter.UserAdapter
import com.macoev.aadstudyproject.databinding.MainFragmentBinding
import com.macoev.aadstudyproject.utils.Event
import com.macoev.aadstudyproject.utils.EventObserver
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    private val viewModel: UserViewModel by activityViewModels()

    private val adapter by lazy {
        UserAdapter(viewModel.repository) { viewModel.selectedUser.value = Event(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.rv.adapter = adapter
        viewModel.selectedUser.distinctUntilChanged().observe(requireActivity(), EventObserver {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToUserDetailFragment())
        })
    }
}