package com.example.benji.benstimer.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.benji.benstimer.R

import kotlinx.android.synthetic.main.fragment_timer.*
import java.util.*
import java.util.concurrent.TimeUnit


class TimerFragment : Fragment() {

    val TAG = "TimerFragment"

    var currentSecs: Int? = null
    var currentMins: Int? = null

    var isTimerRunning = false

    //var time: String = "20000"

    var time = "20000"

    var timer: Timer?=null


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

        timer_stop_reset_button.visibility = View.INVISIBLE

        seconds_picker.maxValue = 60
        seconds_picker.minValue = 0
        seconds_picker.setOnLongPressUpdateInterval(1200)

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
                //startTimer()
                startOtherTimer()
                timer_stop_reset_button.text = "STOP TIMER"

                if(timer_stop_reset_button.visibility == View.INVISIBLE)
                {
                    timer_stop_reset_button.visibility = View.VISIBLE
                }
            }
        }

        timer_stop_reset_button.setOnClickListener {
            if(isTimerRunning)
            {
                //stopTimer()

                timer?.onFinish()
                timer?.cancel()

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


    //Call this method to start timer on activity start
    private fun startOtherTimer(){
        time = "${seconds_picker?.value}000"
        timer = Timer(time.toLong())
        timer?.start()
        isTimerRunning = true
    }

    inner class Timer(miliis:Long) : CountDownTimer(miliis,1000){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
            time_left_text_view.text = "0"
            timer_stop_reset_button.visibility = View.VISIBLE
            isTimerRunning = false
        }

        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
            time_left_text_view.text = "${+millisUntilFinished/1000}"
        }
    }


}
