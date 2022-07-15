package com.im_geokjeong.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.im_geokjeong.R
import com.im_geokjeong.databinding.FragmentFavoriteBinding
import com.im_geokjeong.databinding.ItemFavoriteBinding
import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory
import com.im_geokjeong.ui.rentalpost.PostViewModel
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFavoriteListAdapter()
        viewModel.openPersonEvent.observe(viewLifecycleOwner, EventObserver {
            openFavoriteDetail(it.id)
        })
    }

    private fun setFavoriteListAdapter() {
        val favoriteAdapter = FavoriteAdapter(viewModel)
        binding.rvFavorite.adapter = favoriteAdapter
        viewModel.items.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
        }

    }

    private fun openFavoriteDetail(id: Int) {
        findNavController().navigate(
            R.id.action_navigation_favorite_to_navigation_rental_person_detail, bundleOf(
                "id" to id
            )
        )
    }
}