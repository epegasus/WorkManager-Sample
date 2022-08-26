package dev.pegasus.workmanager.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.pegasus.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}