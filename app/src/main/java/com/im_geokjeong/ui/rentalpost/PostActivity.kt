package com.im_geokjeong.ui.rentalpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.im_geokjeong.R
import com.im_geokjeong.model.PostPerson
import com.im_geokjeong.ui.common.ViewModelFactory
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.coroutines.launch

class PostActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels { ViewModelFactory(this) }
    private lateinit var person: PostPerson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
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
            }
            //등록 완료되고 어떻게 할지?
        }
        postEditProof.setOnClickListener {
            postProofClicked()
        }
        postEditArea.setOnClickListener {
            postAreaClicked()
        }

    }

    private fun postProofClicked() {
        val items = arrayOf("Y", "N")
        MaterialAlertDialogBuilder(this)
            .setItems(items) { dialog, which ->
                postEditProof.text = items[which]
            }
            .show()
    }

    private fun postAreaClicked() {
        val items = arrayOf("포항시", "상주시", "경주시", "김천시", "안동시", "구미시", "영주시", "영덕군", "청도군", "칠곡군")
        MaterialAlertDialogBuilder(requireContext())
            .setItems(items) { dialog, which ->
                postEditArea.text = items[which]
            }
            .show()
    }

    private fun mappingLocation(korean: String): Boolean {
        return when (korean) {
            "Y" -> true
            else -> false
        }
    }

}