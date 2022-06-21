package com.im_geokjeong.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.im_geokjeong.R
import java.text.DecimalFormat

@BindingAdapter("priceAmount")
fun applyPriceFormat(view: TextView, price: Int){
    val decimalFormat = DecimalFormat("#,###")
    view.text = view.context.getString(R.string.unit_discount_currency, decimalFormat.format(price))
}