package com.example.videorecordingvoicerecordingqrscanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.videorecordingvoicerecordingqrscanner.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding:FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        binding.qrScanner.setOnClickListener {
            findNavController().navigate(R.id.qrScannerFragment)
        }

        binding.VideoRecorder.setOnClickListener {
            findNavController().navigate(R.id.videoOpenCameraAndReadVideoFragment)
        }

        binding.audioRecorder.setOnClickListener {
            findNavController().navigate(R.id.audioRecorderFragment)
        }

        return binding.root
    }

}