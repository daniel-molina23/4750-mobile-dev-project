package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple fragment class which is called every time the user clicks add exercise
 */
class ExerciseFragment(fitnessDay: FitnessDay) : Fragment()
{

    private lateinit var weightTrainEditText: EditText
    private lateinit var cardioEditText: EditText
    private lateinit var mainMenuButton: Button
    private lateinit var weightText: String
    private lateinit var cardioText: String

    //Used As a Key to get the Correct Data Base Item
    //private var currentDate: Date = date

    //Getting a Reference to the Singleton (So that we can access the data base)
    private var fitnessDayRepo: FitnessDayRepository = FitnessDayRepository.get()

    private var fitnessDay: FitnessDay = fitnessDay
    //private var fitnessDay: LiveData<FitnessDay?> = fitnessDayRepo.getFitnessDay(date)

    //Used to pass data to the Activity
    private lateinit var dataPasser: OnDataPass

    /**Used to initialize the dataPasser Object */
    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        this.dataPasser = context as OnDataPass
    }

    /**Helper Method Used to Pass Data to the Activity*/
    private fun passData(data: FragmentToSwitchTo, fitnessDay: FitnessDay)
    {
        dataPasser.onDataPass(data, fitnessDay)
    }

    /**
     * Method Used to Link the Layout with the Fragment
     * As well as Initialize The Widgets
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.exer_cal_enter, container,false)

        /**Initializing the Widget References with their Associated widgets*/
        this.weightTrainEditText = view.findViewById(R.id.weight_train__cal)
        this.cardioEditText = view.findViewById(R.id.cardio_cal)
        this.mainMenuButton = view.findViewById(R.id.exercise_to_activity_menu_btn)

        /**Checking if the User Clicked the Main Menu Button */
        this.mainMenuButton.setOnClickListener { view: View ->
            //Kill the method so that the onDestroy
            // Should update database to show new exercise calories based on added exercise
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        //Switching Back to the FitnessDayFragment
        this.mainMenuButton.setOnClickListener { view ->
            this.passData(FragmentToSwitchTo.FITNESS_DAY_FRAGMENT, fitnessDay)
        }

        if(fitnessDay.exerciseCalories.computeTotalCalories() != 0) {
            if (fitnessDay.exerciseCalories.getValue("weight") != 0)
                weightTrainEditText.setText(fitnessDay.exerciseCalories.getValue("weight").toString())
            if (fitnessDay.exerciseCalories.getValue("cardio") != 0)
                cardioEditText.setText(fitnessDay.exerciseCalories.getValue("cardio").toString())
        }


    }

    override fun onStop() {
        super.onStop()

        weightText = weightTrainEditText.text.toString()
        cardioText = cardioEditText.text.toString()

        if(weightTrainEditText.text.toString() == "")
            weightText = "0"
        if(cardioEditText.text.toString() == "")
            cardioText = "0"

        // Creates a CustomFoodHashMap for the given text
        var exerciseHashMap: CustomExerciseHashMap =
            CustomExerciseHashMap("weight/" + weightText +
                    ",cardio/" + cardioText)

        fitnessDay.exerciseCalories = exerciseHashMap

        fitnessDayRepo.updateFitnessDay(fitnessDay)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}