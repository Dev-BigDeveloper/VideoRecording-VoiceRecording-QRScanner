package com.example.videorecordingvoicerecordingqrscanner.videoView

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.example.videorecordingvoicerecordingqrscanner.databinding.FragmentVideoOpenCamerAndReadVideoBinding

class VideoOpenCameraAndReadVideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoOpenCamerAndReadVideoBinding
    private var ourRequestCode:Int = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoOpenCamerAndReadVideoBinding.inflate(inflater,container,false)

        val mediaCollection = MediaController(requireContext())
        mediaCollection.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaCollection)

        binding.button.setOnClickListener {
            startVideo()
        }

        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun startVideo(){
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent,ourRequestCode)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ourRequestCode && resultCode == AppCompatActivity.RESULT_OK){
            val videoUri = data?.data
            binding.videoView.setVideoURI(videoUri)
            binding.videoView.start()
        }
    }

}