package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.os.Bundle
import android.util.Log
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
 * A simple fragment class which is called every time the user clicks add food
 */
class FoodFragment(fitnessDay: FitnessDay) : Fragment()
{

    private lateinit var breakfastEditText : EditText
    private lateinit var lunchEditText: EditText
    private lateinit var dinnerEditText: EditText
    private lateinit var snackEditText: EditText
    private lateinit var menuButton: Button

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
          this.passData(FragmentToSwitchTo.FITNESS_DAY_FRAGMENT, fitnessDay)
        }

    }

    override fun onStop() {
        super.onStop()

        // Creates a CustomFoodHashMap for the given text
        var foodHashMap: CustomFoodHashMap =
            CustomFoodHashMap("breakfast/" + breakfastEditText.text +
                    ",lunch/" + lunchEditText.text + ",dinner/" + dinnerEditText.text + ",snack/" + snackEditText.text)

        fitnessDay.foodCalories = foodHashMap

        fitnessDayRepo.updateFitnessDay(fitnessDay)


    }

    /**Gets called when the Fragment will be destroyed */
    override fun onDestroy() {

        super.onDestroy()

        /**Define the logic which copy the test in all the edit Text */



    }

}