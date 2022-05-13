package com.thirdwinter.slitechassessment.ui.navigations

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.data.recyclerview_helper.GeneralAdapter
import com.data.recyclerview_helper.MainViewHolder
import com.data.recyclerview_helper.SuperClickListener
import com.google.android.material.snackbar.Snackbar
import com.kcoding.recyclerview_helper.SuperEntity
import com.thirdwinter.slitechassessment.db.architecture.model.Competition
import com.thirdwinter.slitechassessment.db.architecture.viewmodels.FootballViewModel
import com.thirdwinter.slitechassessment.util.LoadState
import dagger.hilt.android.AndroidEntryPoint
import slitechassessment.R
import slitechassessment.databinding.CompetitionComponentBinding
import slitechassessment.databinding.FragmentCompetitionsBinding

@AndroidEntryPoint
class CompetitionsFragment : Fragment(), SuperClickListener, GeneralAdapter.ViewHolderPlug {


    lateinit var binding: FragmentCompetitionsBinding
    var competitionList: List<SuperEntity> = listOf()
    val generalAdapter = GeneralAdapter()
    val viewModel: FootballViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.insertCompetitions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)

        generalAdapter.apply {
            superClickListener = this@CompetitionsFragment
            viewHolderPlug = this@CompetitionsFragment
        }

        binding.competitionsRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = generalAdapter
        }

        viewModel.competitions.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                viewModel.publishLoadState(LoadState.DONE)

            generalAdapter.items = it
            competitionList = it
            generalAdapter.notifyDataSetChanged()

        }

        viewModel.loadState.observe(viewLifecycleOwner) {
            when (it) {
                LoadState.DONE -> binding.loader.visibility = View.GONE
                LoadState.LOADING -> binding.loader.visibility = View.VISIBLE
                LoadState.ERROR -> {
                    binding.loader.visibility = View.GONE
                    Snackbar.make(binding.root, "unable to reach server", Snackbar.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    binding.loader.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }


    override fun setPlug(group: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(requireContext())
            .inflate(R.layout.competition_component, group, false)
        return viewHolder(itemView)
    }

    fun viewHolder(view: View): MainViewHolder = object : MainViewHolder(view) {
        val binding = CompetitionComponentBinding.bind(view)
        override fun bindPostType(
            types: SuperEntity,
            context: Context,
            clickListener: SuperClickListener
        ) {
            val competition = types as Competition
            val currentSeason = competition.currentSeason
            binding.startDate.text = if (currentSeason != null) currentSeason.startDate else ""
            binding.country.text = if (competition.area != null) competition.area.name else ""
            binding.leagueName.text = competition.name


            binding.root.setOnClickListener {
                clickListener.onClickItem(layoutPosition)
            }
        }
    }


    override fun onClickItem(position: Int) {

        val competition = competitionList[position] as Competition
        findNavController().navigate(
            CompetitionsFragmentDirections.actionCompetitionsFragmentToCompetitionTeams(
                competition.id,
                competition.name!!
            )
        )

    }


}