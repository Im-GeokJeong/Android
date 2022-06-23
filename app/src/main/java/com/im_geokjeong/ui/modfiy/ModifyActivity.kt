package com.im_geokjeong.ui.modfiy

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.im_geokjeong.R
import com.im_geokjeong.model.Person
import com.im_geokjeong.ui.MainActivity
import com.im_geokjeong.ui.common.ViewModelFactory
import com.im_geokjeong.ui.rentalpersondetail.RentalPersonDetailFragment
import kotlinx.android.synthetic.main.activity_modify.*
import kotlinx.coroutines.launch

class ModifyActivity : AppCompatActivity(), RentalPersonDetailFragment.OnDataPassListener {
    private val viewModel: ModifyViewModel by viewModels {
        ViewModelFactory(
            this
        )
    }
    private var postId: Int = 0
    private lateinit var person: Person
    override fun onDataPass(data: Int) {
        postId = data
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        postId = intent.getIntExtra("id", 0)

        viewModel.loadPersonDetail(postId)
        viewModel.items.observe(this) {
            postEditTitle.setText(it.title)
            postEditArea.setText(it.region)
            postEditContent.setText(it.content)
            postEditPhone.setText(it.phoneNumber)
            postEditPrice.setText(it.price.toString())
            postEditProof.text = modifyProof(it.qualification)
            postEditProof.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.black
                )
            )
        }
        postEditProof.setOnClickListener {
            postClicked()
        }
        postComplete.setOnClickListener {
            person = Person(
                id = postId,
                title = postEditTitle.text.toString(),
                content = postEditContent.text.toString(),
                phoneNumber = postEditPhone.text.toString(),
                qualification = mappingLocation(postEditProof.text.toString()),
                region = postEditArea.text.toString(),
                price = postEditPrice.text.toString().toInt()
            )
            lifecycleScope.launch {
                viewModel.updateArticle(person)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun postClicked() {
        val items = arrayOf("Y", "N")
        MaterialAlertDialogBuilder(this)
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

    private fun modifyProof(korean: Boolean): String {
        return when (korean) {
            true -> "Y"
            else -> "N"
        }
    }


}