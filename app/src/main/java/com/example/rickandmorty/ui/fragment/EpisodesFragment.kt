package com.example.rickandmorty.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty.data.entity.GenderAction
import com.example.rickandmorty.data.entity.SeasonAction
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.ui.adapter.EpisodeAdapter
import com.example.rickandmorty.ui.viewmodel.CharactersFragmentViewModel
import com.example.rickandmorty.ui.viewmodel.EpisodeFragmentViewModel
import com.example.rickandmorty.ui.viewmodel.LocationFragmentViewModel
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodesFragment : Fragment() {
    private lateinit var binding: FragmentEpisodesBinding
    lateinit var recyclerViewAdapter: EpisodeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBinding.inflate(layoutInflater, container, false)

        binding.buttonFilter.setOnClickListener {
            filterButtonClick()
        }

        binding.swipeRefresh.setOnRefreshListener {
            initRecyclerView()
            initViewModel()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.filterSeason.setOnCheckedChangeListener(object : ChipGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
                if(checkedId!= View.NO_ID){
                    when(checkedId){
                        binding.statusSeason1.id -> {
                            initSeasonEpisode(SeasonAction.SEASON1)
                        }
                        binding.statusSeason2.id -> {
                            initSeasonEpisode(SeasonAction.SEASON2)
                        }
                        binding.statusSeason3.id -> {
                            initSeasonEpisode(SeasonAction.SEASON3)
                        }
                        binding.statusSeason4.id -> {
                            initSeasonEpisode(SeasonAction.SEASON4)
                        }
                        binding.statusSeason5.id -> {
                            initSeasonEpisode(SeasonAction.SEASON5)
                        }
                    }
                }
            }
        })
        initRecyclerView()
        initViewModel()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rv.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val decoration  = DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = EpisodeAdapter()
            adapter = recyclerViewAdapter
        }
    }

    private fun initViewModel() {
        val viewModel  = ViewModelProvider(this).get(EpisodeFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getEpisodeList().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun initSeasonEpisode(seasonAction: SeasonAction) {
        val viewModel  = ViewModelProvider(this).get(EpisodeFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getGenderCharacter(seasonAction).collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun filterButtonClick(){
        if(binding.filterSeason.visibility == View.GONE)
        {
            binding.filterSeason.visibility = View.VISIBLE
            binding.textViewSeason.visibility = View.VISIBLE
        } else{
            binding.filterSeason.visibility = View.GONE
            binding.textViewSeason.visibility = View.GONE
        }
    }
}