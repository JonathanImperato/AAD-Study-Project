package com.macoev.roomsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.macoev.roomsample.databinding.MainFragmentBinding
import com.macoev.roomsample.utils.EventObserver
import com.macoev.roomsample.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val model: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = model
        model.selectedUser.observe(requireActivity(), EventObserver {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToUserDetailFragment())
        })
    }
}