package com.im_geokjeong.ui.officedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.im_geokjeong.databinding.ItemOfficeDetailBinding
import com.im_geokjeong.model.Machine

class OfficeDetailAdapter :
    ListAdapter<Machine, OfficeDetailAdapter.OfficeDetailItemViewHolder>(
        OfficeDetailDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeDetailItemViewHolder {
        val binding = ItemOfficeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfficeDetailItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfficeDetailItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OfficeDetailItemViewHolder(private val binding: ItemOfficeDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(machine: Machine){
            binding.machine = machine
            binding.executePendingBindings()
        }
    }
}

class OfficeDetailDiffCallback: DiffUtil.ItemCallback<Machine>() {
    override fun areItemsTheSame(oldItem: Machine, newItem: Machine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Machine, newItem: Machine): Boolean {
        return oldItem == newItem
    }
}
