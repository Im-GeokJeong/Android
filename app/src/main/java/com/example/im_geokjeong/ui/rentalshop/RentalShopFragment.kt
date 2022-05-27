package com.example.im_geokjeong.ui.rentalshop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.im_geokjeong.R

class RentalShopFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rental_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn = view.findViewById<TextView>(R.id.btn_hello)
        btn.setOnClickListener {
            onSlideUpDialog()
        }
    }

    private fun onSlideUpDialog() {
        var contentView: View =
            (requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.popup_slideup,
                null
            )
        val slideupPopup = SlideUpDialog.Builder(requireActivity())
            .setContentView(contentView)
            .create()

        /*slideupPopup.setOnCancelListener{
            Log.d("DIALOG", "close")
        }*/
        contentView.findViewById<TextView>(R.id.btn_popup_detail).setOnClickListener {
            val shopName = contentView.findViewById<TextView>(R.id.tv_popup_rental_name)
            openRentalShopDetail(shopName.toString())
            slideupPopup.dismissAnim()
        }
        slideupPopup.show()
    }

    private fun openRentalShopDetail(rentalShopId: String) {
        findNavController().navigate(
            R.id.action_rental_shop_to_rental_shop_detail, bundleOf(
                "rental_shop_id" to rentalShopId
            )
        )
    }
}