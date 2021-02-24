package com.macoev.roomsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.macoev.roomsample.R
import com.macoev.roomsample.databinding.MainFragmentBinding
import com.macoev.roomsample.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class MainFragment : Fragment() {

    private  var binding: MainFragmentBinding?=null
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
            it?.let {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToUserDetailFragment())
            }
        })
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}