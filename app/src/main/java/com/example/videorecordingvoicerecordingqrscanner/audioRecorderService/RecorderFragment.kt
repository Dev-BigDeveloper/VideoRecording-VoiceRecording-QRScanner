package com.example.videorecordingvoicerecordingqrscanner.audioRecorderService

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.videorecordingvoicerecordingqrscanner.R
import com.example.videorecordingvoicerecordingqrscanner.databinding.FragmentRecorderBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class RecorderFragment : Fragment() {

    private lateinit var binding: FragmentRecorderBinding
    private var recorder: MediaRecorder? = null
    private var isRecording  = false
    private var fileName = String()
    private var path = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/VRecorder")

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tag = "####################"
        binding = FragmentRecorderBinding.inflate(inflater, container, false)
        isRecording = false
        askRunTimePermissions()
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val date: String = format.format(Date())

        path.mkdirs()

        fileName = "$path/recording_$date.amr"
        if (path.exists()) {
            path.mkdirs()
        }

        if (ContextCompat.checkSelfPermission(requireContext(),
                RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        } else {
            startRecording()
        }

        binding.btnRec.setOnClickListener {
            if (!isRecording) {
                try {
                    binding.gifView.visibility = View.VISIBLE
                    binding.timeRec.base = SystemClock.elapsedRealtime()
                    binding.timeRec.start()
                    binding.textRecStart.text = "Recording ..."
                    binding.btnRec.setImageResource(R.drawable.ic_stop)
                    isRecording = true
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.d(tag, "onCreateView: $e")
                    Toast.makeText(requireContext(), "Could Record", Toast.LENGTH_SHORT).show()
                }
            } else if (isRecording) {
                stopRecording()
                binding.gifView.visibility = View.GONE
                binding.timeRec.base = SystemClock.elapsedRealtime()
                binding.timeRec.stop()
                binding.textRecStart.text = ""
                binding.btnRec.setImageResource(R.drawable.ic_record)
                isRecording = false
            }
        }

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
        recorder!!.setOutputFile(fileName)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder!!.prepare()
        recorder!!.start()
    }

    private fun stopRecording() {
       try {
           recorder?.stop()
           recorder?.release()
           recorder = null
       }catch (e:IOException){
           e.printStackTrace()
       }
    }

    private fun askRunTimePermissions() {
        Dexter.withContext(requireContext()).withPermissions(
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
            RECORD_AUDIO
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                Toast.makeText(requireContext(), "Granted !!!", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1!!.continuePermissionRequest()
            }
        })
    }
}