package com.im_geokjeong.ui.office

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.im_geokjeong.databinding.PopupSlideupBinding
import com.im_geokjeong.model.Office

class OfficeAdapter : ListAdapter<Office, OfficeAdapter.OfficeItemViewHolder>(
    OfficeDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfficeAdapter.OfficeItemViewHolder {
        val binding =
            PopupSlideupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfficeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfficeItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OfficeItemViewHolder(private val binding: PopupSlideupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(office: Office) {
            binding.office = office
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