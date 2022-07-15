package com.im_geokjeong.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.im_geokjeong.databinding.ItemFavoriteBinding
import com.im_geokjeong.model.Favorite

class FavoriteAdapter(private val viewModel: FavoriteViewModel):ListAdapter <Favorite,FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallback()){

    private lateinit var binding : ItemFavoriteBinding

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.viewModel=viewModel
            binding.favorite=favorite
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class FavoriteDiffCallback: DiffUtil.ItemCallback<Favorite>(){
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.id == newItem.id
    }

}
