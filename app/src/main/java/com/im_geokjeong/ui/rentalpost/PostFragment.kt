package com.im_geokjeong.ui.rentalpost

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
import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.ui.common.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.launch

class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels { ViewModelFactory(requireContext()) }
    private lateinit var person: PostPerson


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postComplete.setOnClickListener {
            person = PostPerson(
                title = postEditTitle.text.toString(),
                content = postEditContent.text.toString(),
                phoneNumber = postEditPhone.text.toString(),
                qualification = mappingLocation(postEditProof.text.toString()),
                region = postEditArea.text.toString(),
                pin = postEditPin.text.toString(),
                price = postEditPrice.text.toString().toInt()
            )
            lifecycleScope.launch {
                viewModel.uploadPost(person)
                finishPost()
            }
            //등록 완료되고 어떻게 할지?
        }
        postEditProof.setOnClickListener {
            postClicked()
        }

    }

    private fun postClicked() {
        val items = arrayOf("Y", "N")
        MaterialAlertDialogBuilder(requireContext())
            .setItems(items) { dialog, which ->
                postEditProof.text = items[which]
            }
            .show()
    }

    private fun mappingLocation(korean: String): Boolean {
        return when (korean) {
            "Y" -> true
            else -> false
        }
    }

    private fun finishPost() {
        findNavController().navigate(
            R.id.action_navigation_add_post_to_navigation_office, bundleOf(
            )
        )
    }
}