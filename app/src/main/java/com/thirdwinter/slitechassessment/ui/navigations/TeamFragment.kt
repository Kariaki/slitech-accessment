package com.thirdwinter.slitechassessment.ui.navigations

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.data.recyclerview_helper.GeneralAdapter
import com.data.recyclerview_helper.MainViewHolder
import com.data.recyclerview_helper.SuperClickListener
import com.google.android.material.snackbar.Snackbar
import com.kcoding.recyclerview_helper.SuperEntity
import com.thirdwinter.slitechassessment.db.architecture.model.Squad
import com.thirdwinter.slitechassessment.db.architecture.model.Team
import com.thirdwinter.slitechassessment.db.architecture.viewmodels.FootballViewModel
import com.thirdwinter.slitechassessment.util.LoadState
import dagger.hilt.android.AndroidEntryPoint
import slitechassessment.R
import slitechassessment.databinding.CompetitionTeamComponentBinding
import slitechassessment.databinding.FragmentTeamBinding
import slitechassessment.databinding.TeamSquadComponentBinding

@AndroidEntryPoint
class TeamFragment : Fragment(), SuperClickListener,
    GeneralAdapter.ViewHolderPlug {


    lateinit var binding: FragmentTeamBinding
    private val viewModel: FootballViewModel by viewModels()
    var teamList: List<SuperEntity> = listOf()
    val generalAdapter = GeneralAdapter()

    val args: TeamFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.insertTeamSquad(args.teamId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTeamBinding.inflate(layoutInflater)
        generalAdapter.apply {
            superClickListener = this@TeamFragment
            viewHolderPlug = this@TeamFragment
        }

        binding.teamRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = generalAdapter
        }

        /**
         * populate team squad from viewmodel to recyclerview
         */
        viewModel.getTeamSquad(args.teamId).observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                generalAdapter.items = it
                teamList = it
                generalAdapter.notifyDataSetChanged()
            }
            viewModel.publishLoadState(LoadState.DONE)

        }

        displayTeamDetails()

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

    /**
     * show team details on UI
     */
    private fun displayTeamDetails() {
        val team = args.team

        Glide.with(requireContext()).load(team.crestUrl).placeholder(R.drawable.team_place_holder)
            .into(binding.teamCrest)
        binding.address.text = team.address
        binding.founded.text = team.founded.toString()
        binding.phone.text = team.phone
        binding.website.text = team.website
        binding.nickname.text = team.shortName
        binding.email.text = team.email

    }

    /**
     * this plug is used to connect the view to the viewholder of the recyclerview
     */
    override fun setPlug(group: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(requireContext())
            .inflate(R.layout.team_squad_component, group, false)
        //connecting view to viewholder
        return viewHolder(itemView)
    }

    //for item click if needed
    override fun onClickItem(position: Int) {}

    /*
    this is the recyclerview viewholder.
     */
    fun viewHolder(view: View): MainViewHolder = object : MainViewHolder(view) {
        val binding = TeamSquadComponentBinding.bind(view)

        override fun bindPostType(
            types: SuperEntity,
            context: Context,
            clickListener: SuperClickListener
        ) {
            val team = types as Squad
            binding.name.text = team.name
            binding.position.text = team.position
            binding.country.text = team.nationality
            binding.dateOfbirth.text = team.dateOfBirth
            val nameSplit = team.name?.split(" ")!!

            val s =
                "${nameSplit[0].first()} ${if (nameSplit.size == 1) "" else nameSplit[1].first()}"

            binding.profileNameView.text = s

        }

    }
}