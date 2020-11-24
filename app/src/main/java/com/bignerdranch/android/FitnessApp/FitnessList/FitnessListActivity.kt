package com.bignerdranch.android.FitnessApp.FitnessList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.FitnessApp.FitnessDay.FitnessDayActivity
import com.bignerdranch.android.FitnessApp.R
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitnessList_id"
private const val ARG_FITNESS_CALENDAR_DATE = "fitnessList_date"

/**
 * This Activity is responsible for switching to the FitnessListFragment
 * - Acts as the Container for that fragment class
 * */

private const val TAG = "FitnessListActivity"

class FitnessListActivity : AppCompatActivity(), FitnessListFragment.Callbacks {


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Started onCreate()")

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_list_container)

        /**Starting the FitnessListFragment*/
        if(currentFragment == null)
        {
            val fragment = FitnessListFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_list_container, fragment)
                .addToBackStack(null) //Adding it to the BackStack So that it will return to the FitnessDayFragment After It Views What it needs to View
                .commit()
        }//end if
    }

    /**Might Need TO Delete This Method Since We Will Come Back To the FitnessDayFragment Therefore Do Not Need to Go to the FitnessDayActivity */

    override fun changeFitnessDayFragment(date: Date)
    {

        val intent = Intent(this@FitnessListActivity, FitnessDayActivity::class.java)

        var b = Bundle()
        b.putString(ARG_FITNESS_CALENDAR_DATE, date.toString())
        intent.putExtra(ARG_FITNESS_DAY_ID, b) //Put your id to your next Intent

        //start the activity with the serializable id
        startActivity(intent)
        finish()
    }
}