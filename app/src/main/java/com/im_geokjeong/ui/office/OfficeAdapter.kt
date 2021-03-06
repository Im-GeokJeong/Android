package com.im_geokjeong.ui.office

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.im_geokjeong.databinding.ItemOfficeListBinding
import com.im_geokjeong.databinding.PopupSlideupBinding
import com.im_geokjeong.model.Office

class OfficeAdapter(private val clickListener: OfficeListener) : ListAdapter<Office, OfficeAdapter.OfficeItemViewHolder>(
    OfficeDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfficeItemViewHolder {
        val binding =
            ItemOfficeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfficeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfficeItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class OfficeListener(val clickListener: (office: Office) -> Unit){
        fun onClick(office: Office) = clickListener(office)
    }

    class OfficeItemViewHolder(private val binding: ItemOfficeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(office: Office, clickListener: OfficeListener) {
            binding.office = office
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class OfficeDiffCallback : DiffUtil.ItemCallback<Office>() {
    override fun areItemsTheSame(oldItem: Office, newItem: Office): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Office, newItem: Office): Boolean {
        return oldItem == newItem
    }
}