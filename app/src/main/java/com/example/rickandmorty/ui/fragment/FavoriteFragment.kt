package com.example.rickandmorty.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentFavoriteBinding
import com.example.rickandmorty.ui.adapter.CharacterAdapter
import com.example.rickandmorty.ui.adapter.EpisodeAdapter
import com.example.rickandmorty.ui.adapter.FavoriteAdapter
import com.example.rickandmorty.ui.fragment.CharacterDetailsBottomSheet.Companion.IS_FAVORITE_STATE_CHANGE
import com.example.rickandmorty.ui.viewmodel.EpisodeFragmentViewModel
import com.example.rickandmorty.ui.viewmodel.FavoriteFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        viewModel.favoriteList.observe(viewLifecycleOwner){
            binding.rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = FavoriteAdapter(requireContext(),it)
            binding.rv.adapter = adapter
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriteFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getFavorite()
        isFavoriteStateChange()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun isFavoriteStateChange() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(IS_FAVORITE_STATE_CHANGE)?.observe(viewLifecycleOwner) { isChange ->
            if (isChange) {
                viewModel.getFavorite()
            }
        }
    }


}