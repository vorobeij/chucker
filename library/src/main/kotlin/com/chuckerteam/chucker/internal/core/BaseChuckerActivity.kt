package com.chuckerteam.chucker.internal.core

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.internal.data.repository.Di

internal abstract class BaseChuckerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Di.init(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isInForeground = true
    }

    override fun onPause() {
        super.onPause()
        isInForeground = false
    }

    companion object {
        var isInForeground: Boolean = false
            private set
    }

    fun showToast(
        message: String,
        toastDuration: Int = Toast.LENGTH_SHORT,
    ) {
        Toast.makeText(this.applicationContext, message, toastDuration).show()
    }
}

internal abstract class BaseChuckerActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Di.init(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isInForeground = true
    }

    override fun onPause() {
        super.onPause()
        isInForeground = false
    }

    companion object {
        var isInForeground: Boolean = false
            private set
    }

    fun showToast(
        message: String,
        toastDuration: Int = Toast.LENGTH_SHORT,
    ) {
        Toast.makeText(this.applicationContext, message, toastDuration).show()
    }
}
