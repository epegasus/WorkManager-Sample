package dev.pegasus.workmanager.ui.fragments

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dev.pegasus.workmanager.ui.activities.MainActivity

open class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!
    lateinit var globalContext: Context
    lateinit var globalActivity: Activity
    lateinit var mainActivity: Activity

    fun getView(inflater: LayoutInflater, container: ViewGroup?, layout: Int): View {
        _binding = DataBindingUtil.inflate(inflater, layout, container, false)
        initializations()
        return binding.root
    }

    private fun initializations() {
        globalContext = binding.root.context
        globalActivity = globalContext as Activity
        mainActivity = globalActivity as MainActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}