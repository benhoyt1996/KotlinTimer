package com.example.benji.benstimer.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.benji.benstimer.R
import kotlinx.android.synthetic.main.fragment_timer_finished.*


class TimerFinishedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer_finished, container, false)
    }

    companion object {

        fun newInstance(): TimerFragment {
            return TimerFragment()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListners()
    }

    fun setUpListners() {
        button.setOnClickListener{
            fragmentManager!!.popBackStack()
        }
    }

}
