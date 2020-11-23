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
        Log.d("FOOD_FRAGMENT", "Enters the on start")

        //Switching Back to the FitnessDayFragment
        this.menuButton.setOnClickListener { view ->
          if(numericRegex.matches(breakfastEditText.text) &&
              numericRegex.matches(lunchEditText.text) &&
              numericRegex.matches(dinnerEditText.text)){

              this.passData(FragmentToSwitchTo.FITNESS_DAY_FRAGMENT, fitnessDay)
          }
          else{
              Toast.makeText(context, "Please Enter Valid Data\n"
                      + "1 or more fields are incorrect!"
                  , Toast.LENGTH_SHORT).show()
          }

        }

        if(fitnessDay.foodCalories.computeTotalCalories() != 0) {
            if(fitnessDay.foodCalories.getValue("breakfast") != 0)
                breakfastEditText.setText(fitnessDay.foodCalories.getValue("breakfast").toString())
            if(fitnessDay.foodCalories.getValue("lunch") != 0)
                lunchEditText.setText(fitnessDay.foodCalories.getValue("lunch").toString())
            if(fitnessDay.foodCalories.getValue("dinner") != 0)
                dinnerEditText.setText(fitnessDay.foodCalories.getValue("dinner").toString())
        }
    }


    private fun ensureProperCalorieInput(){
        var invalidCount = 0
        breakfastText = if(numericRegex.matches(breakfastEditText.text)){
            breakfastEditText.text.toString()
        } else{
            invalidCount ++
            "0"
        }

        lunchText = if(numericRegex.matches(lunchEditText.text)){
            lunchEditText.text.toString()
        } else{
            invalidCount ++
            "0"
        }

        dinnerText = if(numericRegex.matches(dinnerEditText.text)){
            dinnerEditText.text.toString()
        } else{
            invalidCount ++
            "0"
        }

        if(invalidCount > 0) {
            Toast.makeText(context, "1 or more fields defaulted to \"0\"\n"
                + "Please go back and enter correctly!"
                , Toast.LENGTH_SHORT).show()
        }
    }

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