package com.bignerdranch.android.FitnessApp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity()
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

        //Initializing the Widgets
        addFoodTextView = findViewById(R.id.add_food)
        addExerciseTextView = findViewById(R.id.add_exercise)
        mainMenuButton = findViewById(R.id.btn_mainMenu)
        selectDateButton = findViewById(R.id.btnDatePicker)
        notesTextView = findViewById(R.id.notes_for_day)

        //Adding Code Which Edits the Notes


    }
}