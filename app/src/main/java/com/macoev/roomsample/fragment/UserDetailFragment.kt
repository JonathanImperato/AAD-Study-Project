package com.macoev.roomsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.macoev.roomsample.databinding.UserDetailFragmentBinding
import com.macoev.roomsample.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserDetailFragment : Fragment() {
    
    private lateinit var binding: UserDetailFragmentBinding
    private val viewModel: UserViewModel by sharedViewModel()
//    private val args: UserDetailFragmentArguments by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}