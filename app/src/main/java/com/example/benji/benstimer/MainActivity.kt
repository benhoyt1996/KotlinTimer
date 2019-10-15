package com.example.benji.benstimer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import android.view.MenuItem

import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.benji.benstimer.EventBus.Event
import com.example.benji.benstimer.Fragments.TimerFinishedFragment

import com.example.benji.benstimer.Fragments.TimerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NavigationHost {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        //Apply fragment on StartUp
        if(savedInstanceState == null)
        {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_view, TimerFragment.newInstance(), "timerFragment")
                .commit()
        }

        navView.setNavigationItemSelectedListener(this)
    }

    override fun navigateTo(fragment: Fragment, addToBackStack: Boolean) {

//        val transaction = supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.root_view, fragment)

        //Custom Fragment Animations
        //setCustomAnimations() used w/ 4 parameter overload to enable popBackStack() animations
        val transaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fragment_slide_left_enter, R.anim.fragment_slide_left_exit, android.R.animator.fade_in, android.R.animator.fade_out)
            .replace(R.id.root_view, fragment)


        if(addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//
//        val intent = Intent("SOME_TAG").apply { putExtra("KEY_CODE", keyCode) }
//
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        EventBus.getDefault().post(Event(keyCode))

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        EventBus.getDefault().post(Event(keyCode))
        //EventBus.getDefault().postSticky(Event(keyCode))
        return super.onKeyUp(keyCode, event)
    }


    @Subscribe
    fun onTimerFinished(event: Event) {
        Log.d(TAG, "EventBus Triggered...")
        if(event.keyCode == 911)
        {
            Log.d(TAG, "Navigating to TimerFinishedFragment...")
            navigateTo(TimerFinishedFragment.newInstance(), true)

        }
    }


    //Subscribe to EventBus
    override fun onStart() {
        Log.d(TAG, "Registering EventBus")
        EventBus.getDefault().register(this)
        super.onStart()
    }

    //Unsubscribe from EventBus
    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

}
