package com.example.rickandmorty.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import com.example.rickandmorty.ui.adapter.CharacterAdapter
import com.example.rickandmorty.ui.adapter.LocationAdapter
import com.example.rickandmorty.ui.viewmodel.CharactersFragmentViewModel
import com.example.rickandmorty.ui.viewmodel.LocationFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LocationsFragment : Fragment() {
    private lateinit var binding: FragmentLocationsBinding
    lateinit var recyclerViewAdapter: LocationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsBinding.inflate(layoutInflater, container, false)

        initRecyclerView()
        initViewModel()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
            val decoration  = DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = LocationAdapter()
            adapter = recyclerViewAdapter
        }
    }

    private fun initViewModel() {
        val viewModel  = ViewModelProvider(this).get(LocationFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getLocationList().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }
}