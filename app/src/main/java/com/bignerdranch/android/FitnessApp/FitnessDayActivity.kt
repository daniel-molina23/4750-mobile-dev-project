package com.bignerdranch.android.FitnessApp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val TAG = "FitnessDayActivity"

/**
 *
 * This Activity Handles the Fragments for the
 * Main Menu , AddFood, Add Exercise, And DatePicker Fragments
 * */
class FitnessDayActivity : AppCompatActivity(), FitnessDayFragment.Callbacks, OnDataPass
{

    //Initially It Will Switch to FitnessDayFragment
    private var fragmentToSwtichTo: FragmentToSwitchTo = FragmentToSwitchTo.FITNESS_DAY_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.toolbar)) //basic toolbar

        //Telling the fragment manager where to put the fragment in this Activity (FitnessDayActivity)
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fitnessDay_fragment_container) //We Have no Fragments

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

    /**Method Used to Get Data Passed From the Fragments
     * FitnessDayFragment
     * AddFoodFragment
     * ExerciseFragment
     *
     * /**This Will tell us what fragment to go to next */
     *
     * */
    override fun onDataPass(data: FragmentToSwitchTo)
    {
        this.fragmentToSwtichTo = data

        //Switch to the Food_Fragment
        if(this.fragmentToSwtichTo == FragmentToSwitchTo.FOOD_FRAGMENT)
        {
            //Switch to FoodFragment
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fitnessDay_fragment_container, FoodFragment())
                .commit()
        }
        //Switch to the Exercise Fragment
        else if (this.fragmentToSwtichTo == FragmentToSwitchTo.EXERCISE_FRAGMENT)
        {
            //Switch to ExerciseFragment
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fitnessDay_fragment_container, ExerciseFragment())
                .commit()
        }
        //Switching to the FitnessDay Fragment
        else if (this.fragmentToSwtichTo == FragmentToSwitchTo.FITNESS_DAY_FRAGMENT)
        {
            val currentGregorianDate = GregorianCalendar.getInstance().time
            changeFitnessDayFragment(currentGregorianDate)
        }

    }

}