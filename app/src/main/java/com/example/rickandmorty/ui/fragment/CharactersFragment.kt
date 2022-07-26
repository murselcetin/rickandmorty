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
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.adapter.CharacterAdapter
import com.example.rickandmorty.ui.viewmodel.CharactersFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    lateinit var recyclerViewAdapter: CharacterAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)

        initRecyclerView()
        initViewModel()
        return binding.root
    }
    private fun initRecyclerView() {
        binding.rv.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val decoration  = DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = CharacterAdapter()
            adapter = recyclerViewAdapter

        }
    }

    private fun initViewModel() {
        val viewModel  = ViewModelProvider(this).get(CharactersFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

}