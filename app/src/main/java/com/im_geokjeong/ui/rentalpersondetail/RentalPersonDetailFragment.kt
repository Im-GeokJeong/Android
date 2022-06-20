package com.im_geokjeong.ui.rentalpersondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.im_geokjeong.databinding.FragmentRentalPersonDetailBinding
import com.im_geokjeong.ui.common.ViewModelFactory

class RentalPersonDetailFragment : Fragment() {

    private val viewModel: RentalPersonDetailViewModel by viewModels {
        ViewModelFactory(
            requireContext()
        )
    }
    private lateinit var binding: FragmentRentalPersonDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentalPersonDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        requireArguments().getInt("id").let { postId ->
            viewModel.loadPersonDetail(postId)
            viewModel.item.observe(viewLifecycleOwner) {
                val qualification: String = if (it.qualification)
                    "Y"
                else
                    "N"
                binding.privateRentalDetailProof.text = "자격필요여부 : $qualification"
                binding.personDetail = it
            }
        }

    }
}