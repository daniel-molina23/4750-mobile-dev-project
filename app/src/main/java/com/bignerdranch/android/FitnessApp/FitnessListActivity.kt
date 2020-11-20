package com.bignerdranch.android.FitnessApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitnessList_id"
private const val ARG_FITNESS_CALENDAR_DATE = "fitnessList_date"

/**
 * This Activity is responsible for switching to the FitnessListFragment
 * - Acts as the Container for that fragment class
 * */

class FitnessListActivity : AppCompatActivity(), FitnessListFragment.Callbacks{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_list_container)

        if(currentFragment == null){
            val fragment = FitnessListFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_list_container, fragment)
                .commit()
        }//end if
    }

    override fun onFitnessItemSelected(date: Date) {

        val intent = Intent(this@FitnessListActivity, FitnessDayActivity::class.java)

        var b = Bundle()
        b.putString(ARG_FITNESS_CALENDAR_DATE, date.toString())
        intent.putExtra(ARG_FITNESS_DAY_ID, b) //Put your id to your next Intent

        //start the activity with the serializable id
        startActivity(intent)
        finish()
    }
}