package com.example.calculoborracha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler = Handler()
        handler.postDelayed(this::mostrarMainActivity, 3000)
    }

    private fun mostrarMainActivity() {
        val intent = Intent(
                this@SplashActivity, MainActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}