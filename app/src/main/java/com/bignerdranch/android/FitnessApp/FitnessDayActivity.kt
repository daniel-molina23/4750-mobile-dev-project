package com.bignerdranch.android.FitnessApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitness_day_id"

class FitnessDayActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.toolbar)) //basic toolbar

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fitnessDay_fragment_container)
//        val fitnessDayId = intent.getSerializableExtra(ARG_FITNESS_DAY_ID)

        if(currentFragment == null) {
            //gets today's date!!
            val gregorianDate = GregorianCalendar.getInstance()

            //create fragment with both fields!!
            val fragment =
                FitnessDayFragment.newInstance(UUID.randomUUID(), gregorianDate.time)

            //creates and commits fragment transaction
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fitnessDay_fragment_container, fragment)
                .commit()
        }
    }

}