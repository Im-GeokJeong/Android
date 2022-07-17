package com.im_geokjeong.ui.searchcrop

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.im_geokjeong.common.MyLocationManager
import com.im_geokjeong.databinding.DialogCropSearchBinding
import com.im_geokjeong.model.Office
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory
import java.util.ArrayList

class SearchCropDialog : DialogFragment() {
    private val viewModel: MachineListViewModel by viewModels { ViewModelFactory(requireContext()) }
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

        val lm = MyLocationManager.getLocationManager(requireContext())
        val nowLocation = MyLocationManager.getLocation(lm)
        val nowLatitude = nowLocation?.latitude.toString()
        val nowLongitude = nowLocation?.longitude.toString()

        viewModel.openOfficeListEvent.observe(viewLifecycleOwner, EventObserver{
            openMap(it, nowLatitude, nowLongitude)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openMap(machine: String, latitude: String, longitude: String){
        Log.d("openMap", "open clicked")
        viewModel.loadOfficeList(machine, latitude, longitude)

        viewModel.officeList.observe(viewLifecycleOwner){
            Log.d("OfficeList", "$it")
            val officeArrayList = ArrayList<Office>()

            for(office in it){
                officeArrayList.add(office)
            }

            val bundle = Bundle()
            bundle.putSerializable("officeList", officeArrayList)
            SearchCropDialog().arguments = bundle
        }
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