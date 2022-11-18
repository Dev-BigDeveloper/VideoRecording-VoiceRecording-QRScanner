package com.example.videorecordingvoicerecordingqrscanner.audioRecorderService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.videorecordingvoicerecordingqrscanner.databinding.FragmentAudioRecorderBinding
import com.example.videorecordingvoicerecordingqrscanner.viewPagerAdapters.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AudioRecorderFragment : Fragment() {

    private lateinit var binding:FragmentAudioRecorderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAudioRecorderBinding.inflate(inflater,container,false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupViewPager()
        setupTabLayout()

        return binding.root
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when(position){
            0 -> tab.text = "Recorder"
            1 -> tab.text = "Recordings"
            }
        }.attach()

    }

    private fun setupViewPager() {
        val adapter = FragmentPagerAdapter(requireActivity(), 2)
        binding.viewPager.adapter = adapter
    }
}