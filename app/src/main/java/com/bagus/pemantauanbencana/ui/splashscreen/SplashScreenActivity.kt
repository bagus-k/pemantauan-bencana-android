package com.bagus.pemantauanbencana.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bagus.pemantauanbencana.R
import com.bagus.pemantauanbencana.ui.loginpage.LoginActivity
import com.bagus.pemantauanbencana.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}