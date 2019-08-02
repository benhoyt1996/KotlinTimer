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
import java.util.*
import java.util.concurrent.TimeUnit


class TimerFragment : Fragment() {

    val TAG = "TimerFragment"

    var currentSecs: Int? = null
    var currentMins: Int? = null

    var isTimerRunning = false

    //var time: String = "20000"

    var time = "20000"



//    var globalTimer = object : CountDownTimer(time.toLong(), 1000) {
//        override fun onTick(millisUntilFinished: Long) {
//
//            val secs = millisUntilFinished.toString().dropLast(3)
//            //time_left_text_view.text = "$secs"
//            time_left_text_view.text = "${millisUntilFinished / 1000}"
//        }
//
//        override fun onFinish() {
//            Toast.makeText(context, "TIMER IS DONE BEEP BEEP BEEP", Toast.LENGTH_SHORT)
//            cancel()
//            isTimerRunning = false
//        }
//    }

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
                //startTimer()
                startOtherTimer()
                timer_stop_reset_button.text = "STOP TIMER"
            }
        }

        timer_stop_reset_button.setOnClickListener {
            if(isTimerRunning)
            {
                //stopTimer()
                updateTimer()
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

//    fun startTimer() {
//
//
//
//
//            globalTimer.start()
//            isTimerRunning = true
//
//            //createTimerObject()
//
//    }

    //Call this method to start timer on activity start
    private fun startOtherTimer(){
        time = "${seconds_picker?.value}000"
        timer = Timer(time.toLong());
        timer?.start()
    }

    inner class Timer(miliis:Long) : CountDownTimer(miliis,1000){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
            time_left_text_view.text = "Left : 0"

            timer_stop_reset_button.visibility = View.VISIBLE
        }

        override fun onTick(millisUntilFinished: Long) {
            this.millisUntilFinished = millisUntilFinished
            time_left_text_view.text = "Left : "+millisUntilFinished/1000
        }
    }

    //Call this method to update the timer0
    private fun updateTimer(){
        if(timer!=null) {
            val miliis = "${timer?.millisUntilFinished} + ${TimeUnit.SECONDS.toMillis(5)}"
            //Here you need to maintain single instance for previous
            timer?.cancel()
            timer = Timer(miliis.toLong());
            timer?.start()
        }else{
            startOtherTimer()
        }
    }





    //TODO
//    fun stopTimer() {
//
//        globalTimer.cancel()
//        globalTimer.onFinish()
//        isTimerRunning = false
//
//        //MAKE STOP FUNCTIONALITY BETTER
//
//
//    }



}
