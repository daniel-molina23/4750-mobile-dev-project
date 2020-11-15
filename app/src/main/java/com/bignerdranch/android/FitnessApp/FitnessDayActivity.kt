package com.bignerdranch.android.FitnessApp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

private const val ARG_FITNESS_DAY_ID = "fitness_day_id"

class FitnessDayActivity : AppCompatActivity()
{
    //Defining Variables that will be associated with the widgets
    private lateinit var addFoodTextView: TextView
    private lateinit var addExerciseTextView: TextView

    private lateinit var notesTextView: EditText
    // top toolbar items
//    private lateinit var myProfileDropDown: Button
//    private lateinit var selectDateDropDown: Button
//    private lateinit var myListScreen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.toolbar))

//        val fitnessDayId = intent.getSerializableExtra(ARG_FITNESS_DAY_ID)

        //Initializing the Widgets
        addFoodTextView = findViewById(R.id.add_food)
        addExerciseTextView = findViewById(R.id.add_exercise)

        //TODO - this is the button to go back to list_view
//        myProfileDropDown = findViewById(R.id.profile_dropdown)
//        selectDateDropDown = findViewById(R.id.calendar_dropdown)
//        myListScreen = findViewById(R.id.list_view_dropdown)

        notesTextView = findViewById(R.id.notes_for_day)

        //Adding Code Which Edits the Notes



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.fitness_day_nav_bar, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.calendar_dropdown -> {
                Toast.makeText(this, "Calendar Drop Down!"
                    , Toast.LENGTH_SHORT).show()
            }
            R.id.profile_dropdown -> {
                Toast.makeText(this, "Profile Drop Down!"
                    , Toast.LENGTH_SHORT).show()
            }
            R.id.list_view_dropdown -> {
                Toast.makeText(this, "List View Drop Down!"
                    , Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

                //GO to FitnessListActivity
    fun switchToListViewActivity(){
        //go to the FitnessListActivity to view the entire list
            //TODO - define in the manifest how the association should be defined
            //      https://developer.android.com/guide/components/activities/tasks-and-back-stack
            //      "singleTask"  and define newIntent()
        val intent = Intent(this@FitnessDayActivity, FitnessListActivity::class.java)
        startActivity(intent)
        finish() //Killing it so that the activity dies after it switches
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