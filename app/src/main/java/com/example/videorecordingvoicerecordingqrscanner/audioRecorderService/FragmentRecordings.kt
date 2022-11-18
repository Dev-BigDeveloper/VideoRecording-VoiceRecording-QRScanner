package com.example.videorecordingvoicerecordingqrscanner.audioRecorderService

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.videorecordingvoicerecordingqrscanner.adapters.RecordingsAdapter
import com.example.videorecordingvoicerecordingqrscanner.databinding.FragmentRecordingsBinding
import java.io.File
import java.util.*

class FragmentRecordings : Fragment() {

    private lateinit var binding: FragmentRecordingsBinding
    private var list: ArrayList<File>? = null
    private var path =
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/VRecorder")
    private lateinit var recordingsAdapter: RecordingsAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecordingsBinding.inflate(inflater, container, false)

        displayFiles(requireContext())

        return binding.root
    }

    private fun displayFiles(context: Context) {
        binding.rv.setHasFixedSize(true)
        list = ArrayList()
        list!!.addAll(findFile(path))
        recordingsAdapter =
            RecordingsAdapter(list!!, object : RecordingsAdapter.OnSelectedListener {
                override fun onItemSelected(file: File) {
                    val uri: Uri = FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        file
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(uri, "audio/x-wav")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(intent)
                }
            })

        binding.rv.adapter = recordingsAdapter

    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            displayFiles(requireContext())
        }
    }


    private fun findFile(file: File): ArrayList<File> {
        val file1: File = file
        val arrayList = ArrayList<File>()
        val files: Array<File> = file1.listFiles() as Array<File>

        for (file2 in files) {
            if (file2.name.lowercase(Locale.getDefault()).endsWith(".amr")) {
                arrayList.add(file2)
            }
        }

        return arrayList
    }

    override fun onPause() {
        super.onPause()
        displayFiles(requireContext())
    }

    override fun onResume() {
        super.onResume()
        displayFiles(requireContext())
    }

}