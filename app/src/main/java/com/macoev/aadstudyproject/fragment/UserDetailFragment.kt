package com.macoev.aadstudyproject.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.macoev.aadstudyproject.databinding.UserDetailFragmentBinding
import com.macoev.aadstudyproject.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class UserDetailFragment : Fragment() {

    private lateinit var binding: UserDetailFragmentBinding
    private val viewModel: UserViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }
}