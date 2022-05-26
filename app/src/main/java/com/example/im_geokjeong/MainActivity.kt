package com.example.im_geokjeong

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        bottomNavigationView.itemIconTintList = null

    }

    /*private fun onSlideUpDialog() {
        var contentView: View =
            (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.popup_slideup,
                null
            )
        val slideupPopup = SlideUpDialog.Builder(this)
            .setContentView(contentView)
            .create()

        slideupPopup.setOnCancelListener{
            Log.d("DIALOG", "close")
        }
        contentView.findViewById<TextView>(R.id.btn_popup_detail).setOnClickListener {
            startActivity(Intent(this@MainActivity, MapDetailActivity::class.java))
        }
        slideupPopup.show()
    }*/
}