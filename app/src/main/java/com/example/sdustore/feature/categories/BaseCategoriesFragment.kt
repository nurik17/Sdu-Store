package com.example.sdustore.feature.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sdustore.databinding.FragmentBaseCategoriesBinding

open class BaseCategoriesFragment : Fragment() {

    private var _binding: FragmentBaseCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }
}