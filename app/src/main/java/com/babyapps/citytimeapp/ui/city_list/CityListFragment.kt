package com.babyapps.citytimeapp.ui.city_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.citytimeapp.R
import com.babyapps.citytimeapp.databinding.FragmentListBinding
import com.babyapps.citytimeapp.ui.CityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var binding: FragmentListBinding
    private lateinit var cityAdapter: CityTimeAdapter
    private val viewModel: CityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        subscribeObservers()

        // viewModel.setStateEvent(CitiesStateEvent.GetCitiesEvent)
/*
        cityAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("city",it)
            }
            findNavController().navigate(R.id.action_upsertFragment_to_cityListFragment,bundle)

        }

 */
        cityAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("new", it)
            }
            findNavController().navigate(
                R.id.action_cityListFragment_to_upsertFragment,
                bundle
            )
        }

        binding.ivNew.setOnClickListener {
/*
            findNavController().navigate(
                R.id.action_cityListFragment_to_upsertFragment
            )

 */


            findNavController().navigate(R.id.action_cityListFragment_to_upsertFragment)
        }


        // Swipe to delete any city
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val city = cityAdapter.differ.currentList[position]
                viewModel.delete(city)
                Snackbar.make(view, "${city.name} is Removed", Snackbar.LENGTH_LONG).apply {
                    setAction("Un Do") {
                        viewModel.unDo(city)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvCity)
        }

    }


    private fun setRecyclerView() {
        binding.apply {
            rvCity.apply {
                cityAdapter = CityTimeAdapter()
                adapter = cityAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)

            }
        }
    }


    private fun subscribeObservers() {
        println("Subscribed!")

        viewModel.cities.observe(viewLifecycleOwner, Observer {
            cityAdapter.differ.submitList(it)

          //  println("dsffs : ${it.first().name}")
        })
/*
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<City>> -> {
                    // displayProgressBar(false)
                    //appendEvents(datastate.data)
                    dataState.data?.let {
                        cityAdapter.differ.submitList(it.toList())
                    }
                }
                is DataState.Error -> {
                    //  displayProgressBar(false)
                    //  displayError(datastate.exception.message)
                    Toast.makeText(requireContext(), "Error occured!!!!", Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    // displayProgressBar(true)
                }
            }

        })


 */
    }
}