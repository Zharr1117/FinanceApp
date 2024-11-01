package com.example.financeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.example.financeapp.R
import com.example.financeapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireContext().getSharedPreferences("UserSettings", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()

        // Load saved preferences
        val savedCurrency = sharedPrefs.getString("currency", "USD")
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        val notificationsEnabled = sharedPrefs.getBoolean("notifications", true)

        // Set initial values
        binding.currencySpinner.setSelection(
            resources.getStringArray(R.array.currency_options).indexOf(savedCurrency)
        )
        binding.themeSwitch.isChecked = isDarkMode
        binding.notificationSwitch.isChecked = notificationsEnabled

        // Save changes on selection
        binding.currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCurrency = parent?.getItemAtPosition(position).toString()
                editor.putString("currency", selectedCurrency).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("dark_mode", isChecked).apply()
            // Here you could also trigger a theme change in the app
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("notifications", isChecked).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
