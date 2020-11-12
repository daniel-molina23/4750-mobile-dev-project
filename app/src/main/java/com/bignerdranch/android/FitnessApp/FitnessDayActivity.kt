package com.bignerdranch.android.FitnessApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitness_day_id"

class FitnessDayActivity : AppCompatActivity()
{
    //Defining Variables that will be associated with the widgets
    private lateinit var addFoodTextView: TextView
    private lateinit var addExerciseTextView: TextView
    private lateinit var mainMenuButton: Button
    private lateinit var selectDateButton: Button
    private lateinit var notesTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val fitnessDayId = intent.getSerializableExtra(ARG_FITNESS_DAY_ID)

        //Initializing the Widgets
        addFoodTextView = findViewById(R.id.add_food)
        addExerciseTextView = findViewById(R.id.add_exercise)

        //TODO - this is the button to go back to list_view
        mainMenuButton = findViewById(R.id.btn_mainMenu)
        selectDateButton = findViewById(R.id.btnDatePicker)
        notesTextView = findViewById(R.id.notes_for_day)

        //Adding Code Which Edits the Notes


        //GO to FitnessListActivity
        mainMenuButton.setOnClickListener {
            //go to the FitnessListActivity to view the entire list
            //TODO - define in the manifest how the association should be defined
            //      https://developer.android.com/guide/components/activities/tasks-and-back-stack
            //      "singleTask"  and define newIntent()
            val intent = Intent(this@FitnessDayActivity, FitnessListActivity::class.java)
            startActivity(intent)
            finish() //Killing it so that the activity dies after it switches
        }
    }

    companion object{
        fun newInstance(fitnessDayId : UUID){
            val args = Bundle().apply{
                putSerializable(ARG_FITNESS_DAY_ID, fitnessDayId)
            }

            //figure out how to bundle up
//            return ncifnawio
        }
    }
}