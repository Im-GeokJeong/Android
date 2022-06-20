package com.im_geokjeong.ui.rentalperson

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.im_geokjeong.R
import com.im_geokjeong.databinding.ItemPrivateRentalBinding
import com.im_geokjeong.model.Person


class RentalPersonAdapter(private val viewModel: RentalPersonViewModel) :
    ListAdapter<Person, RentalPersonAdapter.RentalPersonViewHolder>(RentalPersonDiffCallback()) {

    private lateinit var binding: ItemPrivateRentalBinding

    inner class RentalPersonViewHolder(private val binding: ItemPrivateRentalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.privateRentalListAddress.text = person.region
            binding.privateRentalListTime.text = "5분전"
            binding.privateRentalListTitle.text = person.title

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalPersonViewHolder {
        binding =
            ItemPrivateRentalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RentalPersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RentalPersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RentalPersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return newItem.equals(oldItem)
    }

}
