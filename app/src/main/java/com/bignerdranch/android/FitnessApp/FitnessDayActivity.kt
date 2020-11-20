package com.bignerdranch.android.FitnessApp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitness_day_id"

class FitnessDayActivity : AppCompatActivity()
{

    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.toolbar)) //basic toolbar

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fitnessDay_fragment_container)

        if(currentFragment == null) {

            //today's date
            val currentGregorianDate = GregorianCalendar.getInstance().time

            //if date not present then create a new one
            if(!fitnessViewModel.checkIfDatePresent(currentGregorianDate)){
                val fitnessDay = FitnessDay()
                fitnessDay.date = currentGregorianDate
                fitnessViewModel.addFitnessDay(fitnessDay)
            }

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