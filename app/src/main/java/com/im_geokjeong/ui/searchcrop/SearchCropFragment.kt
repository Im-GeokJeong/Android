package com.im_geokjeong.ui.searchcrop

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.im_geokjeong.R
import com.im_geokjeong.databinding.DialogCropSearchBinding
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory

class SearchCropDialog : DialogFragment() {
    private val viewModel: SearchCropViewModel by viewModels { ViewModelFactory(requireContext()) }
    private var _binding: DialogCropSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCropSearchBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.etCropSearch.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                searchCrop(binding.etCropSearch.text.toString())
            }
            true
        }

        viewModel.openOfficeListEvent.observe(viewLifecycleOwner, EventObserver{
            Log.d("openMap", "{$it}")
            openMap(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openMap(machine: String) {

        dismiss()
        NavHostFragment.findNavController(this).currentBackStackEntry?.savedStateHandle?.set(
            "machineName",
            machine
        )
    }
    private fun searchCrop(cropName: String) {
        viewModel.loadMachineList(cropName)
        val machineListAdapter = MachineAdapter(viewModel)
        binding.rvMachineList.adapter = machineListAdapter

        viewModel.machineList.observe(viewLifecycleOwner) {
            machineListAdapter.submitList(it)
        }
    }
}