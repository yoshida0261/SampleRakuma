package com.example.samplerakuma

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.samplerakuma.databinding.ActivityMainBinding
import com.example.samplerakuma.ui.brand.BrandAdapter
import com.example.samplerakuma.ui.brand.BrandViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: BrandViewModel
    lateinit var adapter: BrandAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, BrandViewModel.Factory(application))
            .get(BrandViewModel::class.java)

        adapter = BrandAdapter(viewModel)

    }
}
