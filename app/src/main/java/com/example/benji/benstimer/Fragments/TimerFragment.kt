package com.example.benji.benstimer.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.example.benji.benstimer.R
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
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer_stop_reset_button.text = "--- ---"

        seconds_picker.maxValue = 60
        seconds_picker.minValue = 0

        setListners()

    }

    override fun onDetach() {
        super.onDetach()
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance(): TimerFragment {
            return TimerFragment()
        }


    }

    fun setListners() {

        seconds_picker.setOnValueChangedListener { numberPicker, oldValue, newValue ->
            if(!isTimerRunning)
            {
                currentSecs = newValue
                updateTimerText(newValue)
            }
        }

        timer_start_button.setOnClickListener{
            if(!isTimerRunning)
            {
                startTimer()
                timer_stop_reset_button.text = "STOP TIMER"
            }
        }

        timer_stop_reset_button.setOnClickListener {
            if(isTimerRunning)
            {
                stopTimer()
            }
            else
            {

            }
        }
    }


    fun updateTimerText(newValue: Int?) {
        if(!isTimerRunning)
        {
            time_left_text_view.text = newValue.toString()
        }
    }

    fun startTimer() {


        isTimerRunning = true
        val time = "${seconds_picker.value}000"

        val timer = object: CountDownTimer(time.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if(isTimerRunning)
                {
                    val secs = millisUntilFinished.toString().dropLast(3)
                    time_left_text_view.text = "$secs"
                }
                else { cancel()
                        onFinish() }
            }
            override fun onFinish() {
                Toast.makeText(context, "TIMER IS DONE BEEP BEEP BEEP", Toast.LENGTH_SHORT)
            }
        }
        timer.start()
    }


    //TODO
    fun stopTimer() {
        isTimerRunning = false

        //MAKE STOP FUNCTIONALITY BETTER


    }

}
