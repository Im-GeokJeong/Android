package com.example.im_geokjeong

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.hello)
        tv.setOnClickListener{
            onSlideUpDialog()
        }
    }

    private fun onSlideUpDialog() {
        var contentView: View = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.popup_slideup, null)
        val slideupPopup = SlideUpDialog.Builder(this)
            .setContentView(contentView)
            .create()

        /*slideupPopup.setOnCancelListener{
            Log.d("DIALOG", "close")
        }*/
        contentView.findViewById<TextView>(R.id.btnMapDetail).setOnClickListener{
            startActivity(Intent(this@MainActivity, MapDetailActivity::class.java))
        }
        slideupPopup.show()

    }
}