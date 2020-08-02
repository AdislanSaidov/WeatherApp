package com.weather.weatherapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.weather.weatherapp.databinding.FragmentSearchBinding
import com.weather.weatherapp.ui.base.BaseFragment
import com.weather.weatherapp.utils.hideSoftKeyboard

class SearchFragment: BaseFragment(), SearchMvp {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBack.setOnClickListener {
            requireActivity().hideSoftKeyboard()
            findNavController().popBackStack()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(binding.etSearch.text.isEmpty())
                    binding.ivClear.visibility = View.GONE
                else
                    binding.ivClear.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.ivClear.setOnClickListener { binding.etSearch.setText("") }
    }
}