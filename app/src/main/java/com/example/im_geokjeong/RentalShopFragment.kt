package com.example.im_geokjeong

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class RentalShopFragment: Fragment() {
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
            startActivity(Intent(requireActivity(), MapDetailActivity::class.java))
        }
        slideupPopup.show()
    }
}