package com.im_geokjeong.ui.rentalpersondetail

import android.content.Intent
import android.os.Bundle
import android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.im_geokjeong.R
import com.im_geokjeong.databinding.FragmentRentalPersonDetailBinding
import com.im_geokjeong.model.Favorite
import com.im_geokjeong.model.ModifyPerson
import com.im_geokjeong.model.Person
import com.im_geokjeong.ui.common.ViewModelFactory
import com.im_geokjeong.ui.modfiy.ModifyActivity
import kotlinx.android.synthetic.main.fragment_rental_person_detail.*
import kotlinx.coroutines.launch

class RentalPersonDetailFragment : Fragment() {

    private val viewModel: RentalPersonDetailViewModel by viewModels {
        ViewModelFactory(
            requireContext()
        )
    }
    private lateinit var binding: FragmentRentalPersonDetailBinding
    private var personId: Int = 0
    private lateinit var person: Person

    interface OnDataPassListener {
        fun onDataPass(data: Int)
    }
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
            personId = postId
            viewModel.loadPersonDetail(postId)
            viewModel.item.observe(viewLifecycleOwner) {
                val qualification: String = if (it.qualification)
                    "Y"
                else
                    "N"
                binding.privateRentalDetailProof.text = "자격필요여부 : $qualification"
                binding.personDetail = it
                person = it
            }
        }

        privateRentalDetailToolbar.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.modify -> {
                    val et = EditText(requireContext())
                    et.inputType = TYPE_NUMBER_VARIATION_PASSWORD
                    et.width = WRAP_CONTENT
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("수정하시려면 비밀번호를 입력해주세요")
                        .setView(et)
                        .setNegativeButton("취소") { dialog, which ->
                            dialog.cancel()
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            viewModel.getAuth(ModifyPerson(personId, et.text.toString()))
                            viewModel.find.observe(viewLifecycleOwner) { result ->
                                if (result.status == 200) {
                                    //dataPassListener.onDataPass(personId)
                                    val intent = Intent(context, ModifyActivity::class.java)
                                    intent.putExtra("id", personId)
                                    startActivity(intent)
                                    finishDetail()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "비밀번호가 틀렸습니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                        .show()

                    true
                }
                R.id.delete -> {
                    val et = EditText(requireContext())
                    //et.inputType=EditText.

                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("삭제하시려면 비밀번호를 적어주세요")
                        .setView(et)
                        .setNegativeButton("취소") { dialog, which ->
                            dialog.cancel()
                        }
                        .setPositiveButton("확인") { dialog, which ->
                            viewModel.getAuth(ModifyPerson(personId, et.text.toString()))
                            viewModel.find.observe(viewLifecycleOwner) { result ->
                                if (result.status == 200) {
                                    viewModel.deleteArticle(personId)
                                    Toast.makeText(
                                        requireContext(),
                                        "삭제 완료",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finishDetail()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "비밀번호가 틀렸습니다.",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                        .show()
                    true
                }
                R.id.favorite -> {
                    lifecycleScope.launch {
                        viewModel.addFavorite(
                            Favorite(
                                id = person.id,
                                title = person.title,
                                price = person.price,
                                region = person.region
                            )
                        )
                    }
                    Toast.makeText(
                        requireContext(),
                        "즐겨찾기에 추가했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun finishDetail() {
        findNavController().navigate(
            R.id.action_navigation_rental_person_detail_to_navigation_rental_person, bundleOf(
            )
        )
    }

}