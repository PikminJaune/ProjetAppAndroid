package com.tp_apps.presentation.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tp_apps.MainActivity
import com.tp_apps.R
import com.tp_apps.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    //==========================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.counter.observe(this) {
            binding.txvCounter.text = getString(R.string.timer, (it).toString())
            binding.pgbLoading.setProgress(it, true)
        }

        viewModel.isTimerDone.observe(this) {
            if (it) {
                startActivity(MainActivity.newIntent(this))
                this.finish()
            }
        }
    }
}