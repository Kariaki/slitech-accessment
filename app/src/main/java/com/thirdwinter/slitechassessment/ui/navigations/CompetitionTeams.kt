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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.data.recyclerview_helper.GeneralAdapter
import com.data.recyclerview_helper.MainViewHolder
import com.data.recyclerview_helper.SuperClickListener
import com.google.android.material.snackbar.Snackbar
import com.kcoding.recyclerview_helper.SuperEntity
import com.thirdwinter.slitechassessment.db.architecture.model.Team
import com.thirdwinter.slitechassessment.db.architecture.viewmodels.FootballViewModel
import com.thirdwinter.slitechassessment.util.LoadState
import dagger.hilt.android.AndroidEntryPoint
import slitechassessment.R
import slitechassessment.databinding.CompetitionTeamComponentBinding
import slitechassessment.databinding.FragmentCompetitionTeamsBinding

@AndroidEntryPoint
class CompetitionTeams() : Fragment(), SuperClickListener,
    GeneralAdapter.ViewHolderPlug {

    private val viewModel: FootballViewModel by viewModels()
    var teamList: List<SuperEntity> = listOf()
    val generalAdapter = GeneralAdapter()
    lateinit var binding: FragmentCompetitionTeamsBinding
    val arg: CompetitionTeamsArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.insertCompetitionTeams(arg.competitionId)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCompetitionTeamsBinding.inflate(layoutInflater)
        generalAdapter.apply {
            superClickListener = this@CompetitionTeams
            viewHolderPlug = this@CompetitionTeams
        }

        binding.teamRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = generalAdapter
        }

        viewModel.getTeamsForCompetition(arg.competitionId).observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                viewModel.publishLoadState(LoadState.DONE)

            generalAdapter.items = it
            teamList = it
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
            .inflate(R.layout.competition_team_component, group, false)
        return viewHolder(itemView)
    }

    override fun onClickItem(position: Int) {
        val clickedTeam = teamList[position] as Team
        val direction = CompetitionTeamsDirections.actionCompetitionTeamsToTeamFragment(
            clickedTeam.id,
            clickedTeam.name!!, clickedTeam
        )
        findNavController().navigate(direction)
    }

    fun viewHolder(view: View): MainViewHolder = object : MainViewHolder(view) {
        val binding = CompetitionTeamComponentBinding.bind(view)
        override fun bindPostType(
            types: SuperEntity,
            context: Context,
            clickListener: SuperClickListener
        ) {
            val team = types as Team
            Glide.with(context).load(team.crestUrl).placeholder(R.drawable.team_place_holder)
                .into(binding.crestImage)
            binding.root.setOnClickListener {
                clickListener.onClickItem(layoutPosition)
            }

        }

    }
}