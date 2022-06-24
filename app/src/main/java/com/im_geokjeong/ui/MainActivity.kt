package com.im_geokjeong.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.im_geokjeong.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigationMain)
        bottomNavigationView.itemIconTintList = null

        val navController = supportFragmentManager.findFragmentById(R.id.containerMain)?.findNavController()
        navController?.let{
            bottomNavigationView.setupWithNavController(it)
        }
    }
}