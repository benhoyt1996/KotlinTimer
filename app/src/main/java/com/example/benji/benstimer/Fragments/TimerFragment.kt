package com.example.benji.benstimer.Fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.benji.benstimer.EventBus.Event
import com.example.benji.benstimer.R

import kotlinx.android.synthetic.main.fragment_timer.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.lang.StringBuilder
import android.R.id.message
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.example.benji.benstimer.NavigationHost
import org.greenrobot.eventbus.ThreadMode
import kotlin.math.round


class TimerFragment : Fragment() {

    val TAG = "TimerFragment"

    var isHeatupTimerRunning = false
    var isCooldownTimerRunning = false

    // Set arbitrary values
    var heatupTime = "20000"
    var cooldownTime = "20000"

    var heatupTimer: HeatupTimer?=null
    var cooldownTimer: CooldownTimer?=null

    //val reciever = mMessageReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_timer, container, false)

//        val filter = IntentFilter().apply { addAction("SOME_TAG") }
//
//        LocalBroadcastManager.getInstance(context!!).registerReceiver(reciever, filter)

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer_stop_reset_button.visibility = View.INVISIBLE
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

        timer_start_button.setOnClickListener{
           if(number_edit_text.hasFocus() == true)
           {
               number_edit_text.clearFocus()
               val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
               imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
           }

            startHeatTimer()
        }


        timer_stop_reset_button.setOnClickListener {
            if(isHeatupTimerRunning)
            {
                //heatupTimer?.onFinish()
                heatupTimer?.stopHeatTimer()
                heatupTimer?.cancel()
            }
            if (isCooldownTimerRunning)
            {
                cooldownTimer?.stopCoolTimer()
                cooldownTimer?.cancel()
            }
        }

        constraint_layout.setOnClickListener {
            if(number_edit_text.hasFocus() == true || timer_two_edit_text.hasFocus())
            {
                number_edit_text.clearFocus()
                timer_two_edit_text.clearFocus()
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
            }
        }
    }

    @Subscribe
    fun onKeyEvent(event: Event) {
        //called when an event occurs
        Log.d(TAG, "EventBus Triggered...")
        first_timer_text_view.text = "${number_edit_text?.text}"
        second_timer_text_view.text = "${timer_two_edit_text?.text}"

    }

    private fun startHeatTimer() {

        Log.d(TAG, isCooldownTimerRunning.toString() +  isHeatupTimerRunning.toString())
        //  Verify timers are not counting down before attempting to start them
        if(!isHeatupTimerRunning && !isCooldownTimerRunning)
        {
            heatupTime = "${number_edit_text?.text}000"
            heatupTimer = HeatupTimer(heatupTime.toLong())
            heatupTimer?.start()
            isHeatupTimerRunning = true
            timer_stop_reset_button.text = "STOP TIMER"

            setupCooldownTimer()

            if(timer_stop_reset_button.visibility == View.INVISIBLE)
            {
                timer_stop_reset_button.visibility = View.VISIBLE
            }
        }

    }


    fun setupCooldownTimer() {
        cooldownTime = "${timer_two_edit_text?.text}000"
        cooldownTimer = CooldownTimer(cooldownTime.toLong())
    }


    inner class HeatupTimer(miliis:Long) : CountDownTimer(miliis,1000){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
            //first_timer_text_view.text = "0"
            //timer_stop_reset_button.visibility = View.VISIBLE
            isHeatupTimerRunning = false
            first_timer_text_view.text = "0"

            //Start CoolDownTimer
            cooldownTimer?.start()
            isCooldownTimerRunning = true

        }

        fun stopHeatTimer() {
            isHeatupTimerRunning = false
            first_timer_text_view.text = "${number_edit_text.text}"
            second_timer_text_view.text = "${timer_two_edit_text.text}"
        }

//        fun startCoolTimer() {
//            startCoolTimer()
//        }

        override fun onTick(millisUntilFinished: Long) {

            //val d: Double = millisUntilFinished.toDouble()

            first_timer_text_view.text = "${+round(millisUntilFinished.toDouble()/1000)}".replace(".0", "")
        }
    }

    inner class CooldownTimer(miliis:Long) : CountDownTimer(miliis,1000){
        var millisUntilFinished:Long = 0
        override fun onFinish() {
            //second_timer_text_view.text = "0"
            timer_stop_reset_button.visibility = View.VISIBLE
            isCooldownTimerRunning = false
            second_timer_text_view.text = "0"

            bothTimersFinished()
        }

        fun stopCoolTimer() {
            isCooldownTimerRunning = false

            first_timer_text_view.text = "${number_edit_text.text}"
            second_timer_text_view.text = "${timer_two_edit_text.text}"
        }

        override fun onTick(millisUntilFinished: Long) {



            second_timer_text_view.text = "${+round(millisUntilFinished.toDouble()/1000)}".replace(".0", "")
        }
    }

    fun bothTimersFinished() {
        //EventBus.getDefault().post(Event(911))
        (activity as NavigationHost).navigateTo(TimerFinishedFragment(), true)
    }


    //Subscribe to EventBus
    override fun onStart() {
        Log.d(TAG, "Registering EventBus")
        EventBus.getDefault().register(this)
        first_timer_text_view.text = "${number_edit_text?.text}"
        second_timer_text_view.text = "${timer_two_edit_text?.text}"

        super.onStart()
    }

    //Unsubscribe from EventBus
    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }


}
