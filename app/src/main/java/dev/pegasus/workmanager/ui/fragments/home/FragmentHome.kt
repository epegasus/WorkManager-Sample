package dev.pegasus.workmanager.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.pegasus.workmanager.R
import dev.pegasus.workmanager.databinding.FragmentHomeBinding
import dev.pegasus.workmanager.helper.utils.GeneralUtils.navigateTo
import dev.pegasus.workmanager.ui.fragments.BaseFragment

class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return getView(inflater, container, R.layout.fragment_home)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnImmediateHome.setOnClickListener { navigateTo(R.id.fragmentHome, R.id.action_fragmentHome_to_fragmentImmediate) }
        binding.btnDeferrableHome.setOnClickListener { navigateTo(R.id.fragmentHome, R.id.action_fragmentHome_to_fragmentDeferrable) }
    }
}