package com.babyapps.citytimeapp.ui.city_update

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.babyapps.citytimeapp.R
import com.babyapps.citytimeapp.data.City
import com.babyapps.citytimeapp.databinding.FragmentUpdateBinding
import com.babyapps.citytimeapp.ui.CityViewModel
import com.babyapps.citytimeapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {

    private lateinit var binding: FragmentUpdateBinding

    val args: UpdateFragmentArgs by navArgs()

    private val viewModel: CityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentUpdateBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        val city = args.city

        subscribeObservers()
    }

    private fun subscribeObservers() {
        var cities: List<City> = emptyList()


        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<City>> -> {
                    displayProgressBar(false)

                    binding.analogClock.visibility = View.VISIBLE
                    binding.spinnerCity.visibility = View.VISIBLE
                    binding.spinnerColor.visibility = View.VISIBLE
                    binding.spinnerSign.visibility = View.VISIBLE
                    binding.btnSave.visibility = View.VISIBLE
                    binding.btnCancel.visibility = View.VISIBLE

                    dataState.data?.let {

                        var cityNames: List<String> = emptyList()
                        var cityColors: List<String> = emptyList()
                        var cityColorCodes: List<String> = emptyList()
                        var cityTimeDifferences: List<String> = emptyList()


                        //cityAdapter.differ.submitList(it.toList())
                        println("Datastate: ${dataState.data}")

                        cities = dataState.data.toList()

                        cityNames = cities.map { it.name }
                        cityColors = cities.map { it.colorName }
                        cityColorCodes = cities.map { it.colorCode }
                        cityTimeDifferences = cities.map { it.timeDifference }

                        var cityList: List<String> = emptyList()
                        cityList = cities.map {
                            it.name + it.timeDifference
                        }
                        println("Copied :  ----> $cityNames")
                        println("Copied :  ----> $cityColors")
                        println("Copied :  ----> $cityColorCodes")
                        println("Copied :  ----> $cityTimeDifferences")


                        val citySpinnerAdaper = ArrayAdapter<String>(
                            requireContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            cityList
                        )
                        binding.spinnerCity.adapter = citySpinnerAdaper

                        val cityColorNamesSpinnerAdaper = ArrayAdapter<String>(
                            requireContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            cityColors
                        )
                        binding.spinnerColor.adapter = cityColorNamesSpinnerAdaper

                        binding.spinnerCity.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    Toast.makeText(
                                        requireContext(),
                                        "You selected : ${cities[position].name}",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    binding.analogClock.setBackgroundColor(Color.parseColor(cities[position].colorCode))


                                    binding.btnSave.setOnClickListener {
                                        viewModel.insert(cities[position])
                                        findNavController().navigate(R.id.action_addFragment_to_cityListFragment)
                                    }

                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    // Do nothing here!
                                }

                            }

                        binding.spinnerColor.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    Toast.makeText(
                                        requireContext(),
                                        "You selected : ${cities[position].colorName}",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    binding.analogClock.setBackgroundColor(Color.parseColor(cities[position].colorCode))


                                    binding.btnSave.setOnClickListener {
                                        viewModel.insert(cities[position])
                                        findNavController().navigate(R.id.action_addFragment_to_cityListFragment)
                                    }

                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    // Do nothing here!
                                }

                            }

                    }
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }

        })
    }

    private fun displayError(message: String?) {
        if (message != null)
            Toast.makeText(requireContext(), "Some error occured : $message", Toast.LENGTH_LONG)
                .show()
    }


    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.pb.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}