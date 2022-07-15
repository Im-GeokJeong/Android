package com.im_geokjeong.ui.rentalperson

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.im_geokjeong.R
import com.im_geokjeong.databinding.FragmentRentalPersonBinding
import com.im_geokjeong.ui.common.EventObserver
import com.im_geokjeong.ui.common.ViewModelFactory
import com.im_geokjeong.ui.modfiy.ModifyActivity
import com.im_geokjeong.ui.rentalpost.PostActivity
import kotlinx.android.synthetic.main.fragment_rental_person.*

class RentalPersonFragment : Fragment() {

    private val viewModel: RentalPersonViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var binding: FragmentRentalPersonBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRentalPersonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPersonListAdapter()
        viewModel.openPersonEvent.observe(viewLifecycleOwner, EventObserver {
            openPersonDetail(it.id, it.title)
        })

        addPost.setOnClickListener{
            val intent = Intent(context, PostActivity::class.java)
            startActivity(intent)
        }

        privateRentalSearch.setOnEditorActionListener() { v, keyCode, event ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                setArticleAdapter(privateRentalSearch.text.toString())
            }
            true
        }
    }

    private fun setPersonListAdapter() {
        val personAdapter = RentalPersonAdapter(viewModel)
        binding.rvRentalPerson.adapter = personAdapter
        viewModel.items.observe(viewLifecycleOwner) {
            personAdapter.submitList(it)
        }
    }

    private fun setArticleAdapter(title: String){
        viewModel.loadArticleData(title)
        val personAdapter = RentalPersonAdapter(viewModel)
        binding.rvRentalPerson.adapter = personAdapter
        viewModel.items.observe(viewLifecycleOwner){
            personAdapter.submitList(it)
        }
    }

    private fun openPersonDetail(id: Int, title: String) {
        findNavController().navigate(
            R.id.action_navigation_rental_person_to_navigation_rental_person_detail, bundleOf(
                "id" to id,
                "title" to title
            )
        )
    }
}