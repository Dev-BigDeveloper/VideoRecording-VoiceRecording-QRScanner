package com.example.videorecordingvoicerecordingqrscanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videorecordingvoicerecordingqrscanner.databinding.CustomerItemBinding
import java.io.File

class RecordingsAdapter(
    private var list: List<File>,
    private val onSelectedListener: OnSelectedListener
) :
    RecyclerView.Adapter<RecordingsAdapter.ViewHolderRv>() {
    inner class ViewHolderRv(var itemBinding: CustomerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRv {
        val itemVideoBinding =
            CustomerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderRv(itemVideoBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderRv, position: Int) {
        holder.itemBinding.txtName.text = list[position].name
        holder.itemBinding.txtName.isSelected = true
        holder.itemBinding.container.setOnClickListener {
            onSelectedListener.onItemSelected(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnSelectedListener {
        fun onItemSelected(file: File)
    }

}