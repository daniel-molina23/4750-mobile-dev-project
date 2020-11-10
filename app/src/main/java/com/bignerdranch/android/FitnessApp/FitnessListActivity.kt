package com.bignerdranch.android.FitnessApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitness_day_id"

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

    override fun onFitnessItemSelected(dayId: UUID) {

        val intent = Intent(this@FitnessListActivity, FitnessListActivity::class.java)

        var b = Bundle()
        b.putSerializable(ARG_FITNESS_DAY_ID, dayId)
        intent.putExtra(ARG_FITNESS_DAY_ID, b) //Put your id to your next Intent

        //start the activity with the serializable id
        startActivity(intent)
        finish()
    }
}