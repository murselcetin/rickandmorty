package com.example.rickandmorty.ui.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.data.entity.CharacterData
import com.example.rickandmorty.data.entity.FavoriteCharacterData
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBottomSheetBinding
import com.example.rickandmorty.ui.adapter.CharacterEpisodeAdapter
import com.example.rickandmorty.ui.viewmodel.CharacterDetailsBottomSheetViewModel
import com.example.rickandmorty.util.displayMetrics
import com.example.rickandmorty.util.getImage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCharacterDetailsBottomSheetBinding
    private lateinit var viewModel: CharacterDetailsBottomSheetViewModel

    var isFavoriteStateChange: Boolean = false

    companion object {
        const val IS_FAVORITE_STATE_CHANGE = "is_favorite_state_change"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBottomSheetBinding.inflate(layoutInflater, container, false)

        val bundle: CharacterDetailsBottomSheetArgs by navArgs()
        val getCharacter = bundle.character
        val favoriteCharacter = getCharacter.toCharacterData()
        getCharacter.name?.let { viewModel.favoriteControl(it) }
        viewModel.favoriteControl.observe(viewLifecycleOwner) {
            favoriteImageChange(it)
        }

        binding.detailsRv.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

        lifecycleScope.launch {
            viewModel.characterEpisode(getPageNumbers(getCharacter.episode))
        }

        viewModel.episodeList.observe(viewLifecycleOwner){
            val adapter = CharacterEpisodeAdapter(requireContext(), it )
            binding.detailsRv.adapter = adapter
        }

        binding.cardViewFavori.setOnClickListener {
            isFavoriteStateChange = true
            if (viewModel.favoriteControl.value == true) {
                getCharacter.name?.let { it1 -> viewModel.favoriteId(it1) }
                viewModel.favoriteId.observe(viewLifecycleOwner){
                    var favoriteId = it
                    favoriteImageChange(false)
                    viewModel.favoriteDelete(favoriteId)
                }
            } else {
                favoriteImageChange(true)
                viewModel.favoriteAdd(favoriteCharacter)
            }
        }

        getCharacter.image?.let { binding.imageViewCharacterDetails.getImage(it) }
        binding.textViewName.text = getCharacter.name
        binding.textViewStatus.text = getCharacter.status
        binding.textViewGender.text = getCharacter.gender
        binding.textViewSpecies.text = getCharacter.species

        if (getCharacter.status == "Alive") {
            binding.detailsStatus.background =
                ContextCompat.getDrawable(requireContext(), R.color.green2)
        } else {
            binding.detailsStatus.background =
                ContextCompat.getDrawable(requireContext(), R.color.red)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CharacterDetailsBottomSheetViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    fun favoriteImageChange(control: Boolean) {
        if (control) {
            binding.imageViewFavorite.setImageResource(R.drawable.favorite_icon2)
        } else {
            binding.imageViewFavorite.setImageResource(R.drawable.favorite_icon1)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            IS_FAVORITE_STATE_CHANGE, isFavoriteStateChange
        )
        findNavController().popBackStack()
    }

    private fun setupFullHeight(bottomSheet: View) {
        activity?.displayMetrics()?.run {
            val height = heightPixels * 0.9
            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = height.toInt()
            bottomSheet.layoutParams = layoutParams
        }
    }

    fun CharacterData.toCharacterData() = FavoriteCharacterData(
        id = id,
        name = name!!,
        status = status!!,
        species = species!!,
        gender = gender!!,
        image = image!!,
        episode = getPageNumbers(episode)
    )

    fun getPageNumbers(list: List<String>) : String {
        val builder = StringBuilder()
        val regex = "(\\d+)\\D*\\z".toRegex()
        list.forEachIndexed { index, itName ->
            val last =regex.find(itName)?.value
            if (list.size-1 != index) {
                builder.append("$last,")
            } else {
                builder.append(last)
            }
        }
        return builder.toString()
    }
}
