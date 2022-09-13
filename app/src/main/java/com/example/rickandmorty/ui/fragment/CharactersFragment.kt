package com.example.rickandmorty.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.GenderAction
import com.example.rickandmorty.data.entity.StatusAction
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.ui.adapter.CharacterAdapter
import com.example.rickandmorty.ui.adapter.SlideViewPagerAdapter
import com.example.rickandmorty.ui.viewmodel.CharactersFragmentViewModel
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private lateinit var binding: FragmentCharactersBinding
    lateinit var recyclerViewAdapter: CharacterAdapter
    private lateinit var viewPager : ViewPager2
    private lateinit var iv1 : ImageView
    private lateinit var iv2 : ImageView
    private lateinit var iv3 : ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(layoutInflater, container, false)

        viewPager = binding.viewPager
        iv1 = binding.iv1
        iv2 = binding.iv2
        iv3 = binding.iv3
        val images= listOf(R.drawable.slide1, R.drawable.slide2, R.drawable.slide3)
        val adapter = SlideViewPagerAdapter(images)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback (){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeColor()
            }
        })

        binding.buttonFilter.setOnClickListener {
            filterButtonClick()
        }

        binding.swipeRefresh.setOnRefreshListener {
            initRecyclerView()
            initCharacter()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.filterStatus.setOnCheckedChangeListener(object : ChipGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
                if(checkedId!= View.NO_ID){
                    when(checkedId){
                        binding.statusAlive.id -> {
                            initCharacterStatus(StatusAction.ALIVE)
                        }
                        binding.statusDead.id -> {
                            initCharacterStatus(StatusAction.DEAD)
                        }
                        binding.statusUnknown.id -> {
                            initCharacterStatus(StatusAction.UNKNOWN)
                        }
                    }
                }
            }
        })

        binding.filterGender.setOnCheckedChangeListener(object : ChipGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: ChipGroup?, checkedId: Int) {
                if(checkedId!= View.NO_ID){
                    when(checkedId){
                        binding.genderFemale.id -> {
                            initCharacterGender(GenderAction.FEMALE)
                        }
                        binding.genderMale.id -> {
                            initCharacterGender(GenderAction.MALE)
                        }
                        binding.genderGenderless.id -> {
                            initCharacterGender(GenderAction.GENDERLESS)
                        }
                        binding.genderUnknown.id -> {
                            initCharacterGender(GenderAction.UNKNOWN)
                        }
                    }
                }
            }
        })
        initRecyclerView()
        initCharacter()
        return binding.root
    }
    private fun initRecyclerView() {
        binding.rv.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val decoration  = DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = CharacterAdapter(){
                val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailsBottomSheet(character = it)
                findNavController().navigate(action)
            }
            adapter = recyclerViewAdapter
        }
    }

    private fun initCharacter() {
        val viewModel  = ViewModelProvider(this).get(CharactersFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getCharacterList().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun initCharacterStatus(statusAction: StatusAction) {
        val viewModel  = ViewModelProvider(this).get(CharactersFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getStatusCharacter(statusAction).collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun initCharacterGender(genderAction: GenderAction) {
        val viewModel  = ViewModelProvider(this).get(CharactersFragmentViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getGenderCharacter(genderAction).collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun filterButtonClick(){
        if(binding.filterGender.visibility == View.GONE)
        {
            binding.filterGender.visibility = View.VISIBLE
            binding.filterStatus.visibility = View.VISIBLE
            binding.textViewGender.visibility = View.VISIBLE
            binding.textViewStatus.visibility = View.VISIBLE
        } else{
            binding.filterGender.visibility = View.GONE
            binding.filterStatus.visibility = View.GONE
            binding.textViewGender.visibility = View.GONE
            binding.textViewStatus.visibility = View.GONE
        }
    }
    fun changeColor(){
        when(viewPager.currentItem){
            0->{
                iv1.setBackgroundColor(resources.getColor(R.color.white))
                iv2.setBackgroundColor(resources.getColor(R.color.green))
                iv3.setBackgroundColor(resources.getColor(R.color.green))
            }
            1->{
                iv1.setBackgroundColor(resources.getColor(R.color.green))
                iv2.setBackgroundColor(resources.getColor(R.color.white))
                iv3.setBackgroundColor(resources.getColor(R.color.green))
            }
            2->{
                iv1.setBackgroundColor(resources.getColor(R.color.green))
                iv2.setBackgroundColor(resources.getColor(R.color.green))
                iv3.setBackgroundColor(resources.getColor(R.color.white))
            }
        }
    }

    fun getPageNumbers(list : List<String>): String {
        val pageNumber = ""
        list.forEachIndexed { index, itName ->
            val last = itName[itName.length - 1].toString()
            if (list.size != index){
                pageNumber.plus(last.plus(","))
            }else{
                pageNumber.plus(last)
            }
        }
        return pageNumber
    }
}