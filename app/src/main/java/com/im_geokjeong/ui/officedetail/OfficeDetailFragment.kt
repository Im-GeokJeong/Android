package com.im_geokjeong.ui.officedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.im_geokjeong.common.KEY_OFFICE_ID
import com.im_geokjeong.common.KEY_OFFICE_NAME
import com.im_geokjeong.databinding.FragmentOfficeDetailBinding
import com.im_geokjeong.ui.common.ViewModelFactory

class OfficeDetailFragment: Fragment() {
    private val viewModel: OfficeDetailViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentOfficeDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOfficeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        requireArguments().getInt(KEY_OFFICE_ID)?.let{ officeId ->
            setLayout(officeId)
        }

        binding.tvToolbarOfficePhone.setOnClickListener{
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+binding.office?.phoneNumber)
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    private fun setLayout(officeId: Int) {
        viewModel.loadOfficeDetail(officeId)
        val officeDetailAdapter = OfficeDetailAdapter()
        binding.rvOfficeDetail.adapter = officeDetailAdapter
        viewModel.office.observe(viewLifecycleOwner){ office ->
            binding.office = office.data
            officeDetailAdapter.submitList(office.data.machines)
        }
    }
}