package com.im_geokjeong.ui.rentalperson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.im_geokjeong.R
import com.im_geokjeong.ui.common.ViewModelFactory

class RentalPersonFragment : Fragment() {

    private val viewModel: RentalPersonViewModel by viewModels { ViewModelFactory(requireContext()) }

    // private lateinit var binding : Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_person, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.items.observe(viewLifecycleOwner) {
            RentalPersonAdapter(viewModel).submitList(it)
        }
    }
}