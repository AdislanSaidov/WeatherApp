package com.weather.weatherapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.weatherapp.R
import com.weather.weatherapp.data.models.Area
import com.weather.weatherapp.databinding.FragmentSearchBinding
import com.weather.weatherapp.ui.base.BaseFragment
import com.weather.weatherapp.utils.hideSoftKeyboard
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SearchFragment: BaseFragment(), SearchMvp {

    @Inject
    @InjectPresenter
    lateinit var searchPresenter: SearchPresenter
    @Inject
    lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var binding: FragmentSearchBinding

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.let {
                if (it.isEmpty()) {
                    binding.ivClear.visibility = View.GONE
                    binding.tvEmpty.setText(R.string.enterAddress)
                } else {
                    binding.ivClear.visibility = View.VISIBLE
                }
                searchPresenter.onSearchQueryChanged(it.toString())
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBackButton()
        initSearchInput()
        initAreasAdapter()
    }

    private fun initAreasAdapter() {
        binding.rvSearch.run {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchResultAdapter
        }
        searchResultAdapter.setItemClickListener {
            // TODO:
        }
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            requireActivity().hideSoftKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun initSearchInput() {
        binding.etSearch.addTextChangedListener(textWatcher)
        binding.ivClear.setOnClickListener { binding.etSearch.setText("") }
    }

    override fun showAreas(areas: List<Area>) {
        binding.rvSearch.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
        searchResultAdapter.setData(areas)
    }

    override fun showNotFound() {
        binding.rvSearch.visibility = View.GONE
        binding.tvEmpty.visibility = View.VISIBLE
        binding.tvEmpty.setText(R.string.empty_result)
    }

    override fun onDestroyView() {
        binding.etSearch.removeTextChangedListener(textWatcher)
        super.onDestroyView()
    }

    @ProvidePresenter
    fun providePresenter() = searchPresenter
}