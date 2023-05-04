package com.example.sdustore.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sdustore.R
import com.example.sdustore.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater,container,false)


        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewpager)
        binding.nextText.setOnClickListener {
            viewpager?.currentItem = 1
        }
        return binding.root
    }
}