package com.im_geokjeong.ui.searchcrop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.im_geokjeong.databinding.ItemMachineListBinding

class MachineAdapter(private val viewModel: SearchCropViewModel) : ListAdapter<String, MachineAdapter.MachineItemViewHolder>(
    MachineDiffCallback()
) {
    inner class MachineItemViewHolder(private val binding: ItemMachineListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(str: String){
            binding.machine = str
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MachineItemViewHolder {
        val binding = ItemMachineListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MachineItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MachineItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class MachineDiffCallback() : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
