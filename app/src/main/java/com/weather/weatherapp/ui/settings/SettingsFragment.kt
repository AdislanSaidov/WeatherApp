package com.weather.weatherapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weather.weatherapp.R
import com.weather.weatherapp.databinding.FragmentSettingsBinding
import com.weather.weatherapp.ui.base.BaseFragment
import com.weather.weatherapp.utils.Constants.CELSIUS
import com.weather.weatherapp.utils.Constants.FAHRENHEIT
import com.weather.weatherapp.utils.Constants.HPA
import com.weather.weatherapp.utils.Constants.KELVIN
import com.weather.weatherapp.utils.Constants.KM
import com.weather.weatherapp.utils.Constants.MBAR
import com.weather.weatherapp.utils.Constants.METERS
import com.weather.weatherapp.utils.Constants.MILES
import com.weather.weatherapp.utils.Constants.MMHG
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SettingsFragment: BaseFragment(), SettingsMvpView {

    private lateinit var binding: FragmentSettingsBinding
    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.rlTempUnit.setOnClickListener {
            presenter.fetchCurrentTempUnit()
        }
        binding.rlWindSpeedUnit.setOnClickListener {
            presenter.fetchCurrentWindSpeedUnit()
        }
        binding.rlVisibilityUnit.setOnClickListener {
            presenter.fetchCurrentVisibilityUnit()
        }
        binding.rlPressureUnit.setOnClickListener {
            presenter.fetchCurrentPressureUnit()
        }
    }

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter = presenter

    override fun showCurrentTempUnit(unit: Int) {

        val v = layoutInflater.inflate(R.layout.dialog_temp_unit, binding.root, false)

        val radioGroup = v.findViewById<RadioGroup>(R.id.rg_temp)
        when(unit){
            CELSIUS -> radioGroup.check(R.id.rb_celsius)
            FAHRENHEIT -> radioGroup.check(R.id.rb_fahrenheit)
            KELVIN -> radioGroup.check(R.id.rb_kelvin)
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_celsius -> presenter.saveTempChoice(CELSIUS)
                R.id.rb_fahrenheit -> presenter.saveTempChoice(FAHRENHEIT)
                R.id.rb_kelvin -> presenter.saveTempChoice(KELVIN)
            }
        }
        createDialog(v)
    }

    override fun showCurrentVisibilityUnit(unit: Int) {
        val v = layoutInflater.inflate(R.layout.dialog_visibility_unit, binding.root, false)

        val radioGroup = v.findViewById<RadioGroup>(R.id.rg_visibility)
        when(unit){
            KM -> radioGroup.check(R.id.rb_km)
            MILES -> radioGroup.check(R.id.rb_miles)
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_km -> presenter.saveVisibilityChoice(CELSIUS)
                R.id.rb_miles -> presenter.saveVisibilityChoice(MILES)
            }
        }
        createDialog(v)
    }

    override fun showCurrentWindSpeedUnit(unit: Int) {
        val v = layoutInflater.inflate(R.layout.dialog_wind_speed_unit, binding.root, false)

        val radioGroup = v.findViewById<RadioGroup>(R.id.rg_wind_speed)
        when(unit){
            KM -> radioGroup.check(R.id.rb_km)
            METERS -> radioGroup.check(R.id.rb_meters)
            MILES -> radioGroup.check(R.id.rb_miles)
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_km -> presenter.saveWindSpeedChoice(CELSIUS)
                R.id.rb_meters -> presenter.saveWindSpeedChoice(METERS)
                R.id.rb_miles -> presenter.saveWindSpeedChoice(MILES)
            }
        }
        createDialog(v)
    }


    override fun showCurrentPressureUnit(unit: Int) {
        val v = layoutInflater.inflate(R.layout.dialog_pressure_unit, binding.root, false)

        val radioGroup = v.findViewById<RadioGroup>(R.id.rg_pressure)
        when(unit){
            MMHG -> radioGroup.check(R.id.rb_mmhg)
            HPA -> radioGroup.check(R.id.rb_hpa)
            MBAR -> radioGroup.check(R.id.rb_mbar)
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_mmhg -> presenter.savePressureChoice(MMHG)
                R.id.rb_hpa -> presenter.savePressureChoice(HPA)
                R.id.rb_mbar -> presenter.savePressureChoice(MBAR)
            }
        }
        createDialog(v)
    }

    override fun showCurrentUnits(settings: Settings) {
        binding.tvCurrentTempUnit.text = settings.temp
        binding.tvCurrentPressureUnit.text = settings.pressure
        binding.tvCurrentVisibilityUnit.text = settings.visibility
        binding.tvCurrentWindSpeedUnit.text = settings.wind
    }



    private fun createDialog(view: View){
        AlertDialog.Builder(requireContext())
                .setView(view)
                .setNegativeButton(R.string.cancel) { dialog, _ ->  dialog.cancel()}
                .create()
                .show()
    }
}