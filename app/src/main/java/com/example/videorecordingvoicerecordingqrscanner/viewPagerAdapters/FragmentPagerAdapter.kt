package com.example.videorecordingvoicerecordingqrscanner.viewPagerAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videorecordingvoicerecordingqrscanner.audioRecorderService.FragmentRecordings
import com.example.videorecordingvoicerecordingqrscanner.audioRecorderService.RecorderFragment

class FragmentPagerAdapter(manager:FragmentActivity,private val totalCount:Int) : FragmentStateAdapter(manager){

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RecorderFragment()
            1 -> FragmentRecordings()
            else -> {
                RecorderFragment()
            }
        }
    }
}