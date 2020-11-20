package com.bignerdranch.android.FitnessApp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.*
import java.util.concurrent.Executors

private const val ARG_FITNESS_DAY_ID = "fitness_day_id"
private const val TAG = "FitnessDayActivity"

/**
 *
 * This Activity Handles the Fragments for the
 * Main Menu , AddFood, Add Exercise, And DatePicker Fragments
 * */
class FitnessDayActivity : AppCompatActivity()
{

    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }


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
            
            //create fragment with both fields!!
            val fragment =
                FitnessDayFragment.newInstance(currentGregorianDate)

            //creates and commits fragment transaction
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fitnessDay_fragment_container, fragment)
                .commit()
        }
    }

}