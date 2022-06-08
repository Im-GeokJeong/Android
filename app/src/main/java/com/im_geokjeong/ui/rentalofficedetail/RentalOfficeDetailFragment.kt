package com.im_geokjeong.ui.rentalofficedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.im_geokjeong.R
import com.im_geokjeong.common.KEY_OFFICE_ID
import com.im_geokjeong.common.KEY_OFFICE_NAME
import com.im_geokjeong.databinding.FragmentRentalOfficeDetailBinding

class RentalOfficeDetailFragment: Fragment() {

    private lateinit var binding: FragmentRentalOfficeDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentalOfficeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        val officeId = requireArguments().getInt(KEY_OFFICE_ID)
        val officeName = requireArguments().getString(KEY_OFFICE_NAME)
        Log.d("DETAIL", "officeName = $officeName")
        binding.tvToolbarOfficeName.text = officeName
    }
}