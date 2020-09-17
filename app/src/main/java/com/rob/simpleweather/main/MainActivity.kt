package com.rob.simpleweather.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.rob.simpleweather.R
import com.rob.simpleweather.databinding.ActivityMainBinding
import com.rob.simpleweather.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ForecastViewModel>()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnForecastRequest.setOnClickListener {
            viewModel.requestForecastFor("Fossano")
        }
    }
}
