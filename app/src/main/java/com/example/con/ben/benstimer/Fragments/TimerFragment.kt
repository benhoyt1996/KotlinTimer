package com.example.con.ben.benstimer.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker

import com.example.con.ben.benstimer.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_timer.*


class TimerFragment : Fragment() {

    val TAG = "TimerFragment"

    var currentSecs: Int? = null
    var currentMins: Int? = null



    var isTimerRunning = false

    var timeLeft: TextView? = time_left_text_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_timer, container, false)



        //time_picker.setOnTimeChangedListener { timePicker, i, test ->  }




        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)



        if(context != null)
        {

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        minutes_picker.maxValue = 5
//        minutes_picker.minValue = 0

        seconds_picker.maxValue = 60
        seconds_picker.minValue = 0


//        minutes_picker.setOnValueChangedListener { numberPicker, oldValue, newValue ->
//            currentMins = newValue
//            updateTimer()
//        }

        seconds_picker.setOnValueChangedListener { numberPicker, oldValue, newValue ->
            currentSecs = newValue
            updateTimer(newValue)
        }


    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimerFragment.
         */
        // TODO: Rename and change types and number of parameters

        fun newInstance(): TimerFragment {
            return TimerFragment()
        }


    }


    fun updateTimer(newValue: Int?) {
        if(!isTimerRunning)
        {
            time_left_text_view.text = newValue.toString()
        }
    }

}
