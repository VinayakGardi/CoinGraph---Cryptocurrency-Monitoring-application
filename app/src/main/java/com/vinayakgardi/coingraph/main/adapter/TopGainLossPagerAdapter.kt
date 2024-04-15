package com.vinayakgardi.coingraph.main.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vinayakgardi.coingraph.main.ui.TopGainLoseFragment

class TopGainLossPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment)  {
    override fun getItemCount(): Int {
        return 2
        // 2 because you have only 2 fragments to show in the view
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = TopGainLoseFragment()
        val bundle = Bundle()
        bundle.putInt("position",position)
        fragment.arguments = bundle
        return fragment
    }
}