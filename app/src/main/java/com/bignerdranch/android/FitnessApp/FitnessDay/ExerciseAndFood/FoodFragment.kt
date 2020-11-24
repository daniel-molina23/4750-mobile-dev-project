package com.bignerdranch.android.FitnessApp.FitnessDay.ExerciseAndFood

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bignerdranch.android.FitnessApp.FitnessDay.data.CustomFoodHashMap
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import com.bignerdranch.android.FitnessApp.FitnessDay.Repository.FitnessDayRepository
import com.bignerdranch.android.FitnessApp.FitnessDay.FragmentToSwitchTo
import com.bignerdranch.android.FitnessApp.FitnessDay.OnDataPass
import com.bignerdranch.android.FitnessApp.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple fragment class which is called every time the user clicks add food
 */
class FoodFragment(fitnessDay: FitnessDay) : Fragment()
{

    private lateinit var breakfastEditText : EditText
    private lateinit var lunchEditText: EditText
    private lateinit var dinnerEditText: EditText
    private lateinit var menuButton: Button
    private lateinit var breakfastText: String
    private lateinit var lunchText: String
    private lateinit var dinnerText: String

    //in order to validate the inputs made by the user!
    // from 0 calories - 49,999 calories (world record is 35,000 calories in 1 day)
    private val numericRegex = Regex("[0-9]|[1-9][0-9]{1,3}|[1-4][0-9]{1,4}")

    //Used As a Key to get the Correct Data Base Item
    private lateinit var currentDate: Date

    //Getting a Reference to the Singleton (So that we can access the data base)
    private var fitnessDayRepo: FitnessDayRepository = FitnessDayRepository.get()

    // Getting reference to fitness day that data is being added to
    //private var fitnessDay: LiveData<FitnessDay?> = fitnessDayRepo.getFitnessDay(fitnessDay.date)
    private var fitnessDay: FitnessDay = fitnessDay

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        //Getting the Layout of this fragment
        val view = inflater.inflate(R.layout.food_cal_enter, container, false)

        /**Getting the Date From the bundle*/


        /**Initializing the References to the Widgets*/

        this.breakfastEditText = view.findViewById(R.id.breakfast_cal)
        this.lunchEditText = view.findViewById(R.id.lunch_cal)
        this.dinnerEditText = view.findViewById(R.id.dinner_cal)
        this.menuButton = view.findViewById(R.id.food_to_activity_menu_btn)

        return view; //Returning the View
    }

    override fun onStart() {
        super.onStart()

        //Switching Back to the FitnessDayFragment
        this.menuButton.setOnClickListener { view ->
            if(ensureProperCalorieInput())
                this.passData(FragmentToSwitchTo.FITNESS_DAY_FRAGMENT, fitnessDay)
        }


        // Loads text fields with data from database
        if(fitnessDay.foodCalories.computeTotalCalories() != 0) {
            if(fitnessDay.foodCalories.getValue("breakfast") != 0)
                breakfastEditText.setText(fitnessDay.foodCalories.getValue("breakfast").toString())
            if(fitnessDay.foodCalories.getValue("lunch") != 0)
                lunchEditText.setText(fitnessDay.foodCalories.getValue("lunch").toString())
            if(fitnessDay.foodCalories.getValue("dinner") != 0)
                dinnerEditText.setText(fitnessDay.foodCalories.getValue("dinner").toString())
        }
    }


    private fun ensureProperCalorieInput(): Boolean{
        var invalidCount = 0
        var invalidFields = mutableListOf<String>()
        var index = 0

        if(!numericRegex.matches(breakfastEditText.text) && breakfastEditText.text.toString() != "") {
            invalidCount++
            invalidFields.add("Breakfast Field")
        }

        breakfastText = if(numericRegex.matches(breakfastEditText.text))
            breakfastEditText.text.toString()
        else if (breakfastEditText.text.toString() == "")
            "0"
        else
            fitnessDay.foodCalories.getValue("breakfast").toString()

        if(!numericRegex.matches(lunchEditText.text) && lunchEditText.text.toString() != "") {
            invalidCount++
            invalidFields.add("Lunch Field")
        }


        lunchText = if(numericRegex.matches(lunchEditText.text))
            lunchEditText.text.toString()
        else if (lunchEditText.text.toString() == "")
            "0"
        else
            fitnessDay.foodCalories.getValue("lunch").toString()


        if(!numericRegex.matches(dinnerEditText.text) && dinnerEditText.text.toString() != "") {
            invalidCount++
            invalidFields.add("Dinner Field")
        }

        dinnerText = if(numericRegex.matches(dinnerEditText.text))
            dinnerEditText.text.toString()
        else if (dinnerEditText.text.toString() == "")
            "0"
        else
            fitnessDay.foodCalories.getValue("dinner").toString()
        if(invalidCount >0) {
            var fieldError = ""
            var comma = ""
            for(field in invalidFields){
                fieldError += comma + field
                comma = ", "
            }

            Toast.makeText(context, "Invalid fields: " + fieldError + ".\n"
             + "Please check those inputs.", Toast.LENGTH_LONG).show()
            return false
        }
        return true

    }

    /**Storing the User Information in the Data Base When the App Stops */

    override fun onStop() {
        super.onStop()

        //ensures only numeric calories were inputted or else
        //  it will default to 0
        ensureProperCalorieInput()


        // Creates a CustomFoodHashMap for the given text
        val foodHashMap: CustomFoodHashMap =
            CustomFoodHashMap("breakfast/" + breakfastText +
                    ",lunch/" + lunchText + ",dinner/" + dinnerText)


        fitnessDay.foodCalories = foodHashMap

        fitnessDayRepo.updateFitnessDay(fitnessDay)


    }
}