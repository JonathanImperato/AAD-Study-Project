package com.macoev.roomsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.macoev.roomsample.databinding.MainFragmentBinding
import com.macoev.roomsample.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding? = null
    private val model: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding!!.viewModel = model
        model.selectedUser.distinctUntilChanged().observe(requireActivity(), {
            it.getUnhandledContent()?.let {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToUserDetailFragment())
            }
        })
    }
}