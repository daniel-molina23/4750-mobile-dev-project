package com.bignerdranch.android.FitnessApp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "FitnessDayActivity"

/**
 *
 * This Activity Handles the Fragments for the
 * Main Menu , AddFood, Add Exercise, And DatePicker Fragments
 * */
class FitnessDayActivity : AppCompatActivity(), FitnessDayFragment.Callbacks
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.toolbar)) //basic toolbar

        //Telling the fragment manager where to put the fragment in this Activity (FitnessDayActivity)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fitnessDay_fragment_container)

        //If there is no fragment
        if(currentFragment == null)
        {
            //Getting Todays Date as a Date Object
            val currentGregorianDate = GregorianCalendar.getInstance().time

            changeFitnessDayFragment(currentGregorianDate)
        }
    }

    /**
     * We are switching dates, change the fragment you are using
     * Already added to database! Just create transactions.
     * */
    override fun changeFitnessDayFragment(date: Date) {
        val fragment = FitnessDayFragment.newInstance(date)

        //new callback with a new date
        Log.d(TAG, "New date transaction will be added")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fitnessDay_fragment_container, fragment)
            .commit()
    }

}