package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple fragment class which is called every time the user clicks add exercise
 */
class ExerciseFragment : Fragment()
{

    private lateinit var weightTrainEditText: EditText
    private lateinit var cardioEditText: EditText
    private lateinit var mainMenuButton: Button

    //Used As a Key to get the Correct Data Base Item
    private lateinit var currentDate: Date

    //Getting a Reference to the Singleton (So that we can access the data base)
    private var fitnessDayRepo: FitnessDayRepository = FitnessDayRepository.get()

    //Used to pass data to the Activity
    private lateinit var dataPasser: OnDataPass

    /**Used to initialize the dataPasser Object */
    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        this.dataPasser = context as OnDataPass
    }

    /**Helper Method Used to Pass Data to the Activity*/
    private fun passData(data: FragmentToSwitchTo)
    {
        dataPasser.onDataPass(data)
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
            this.passData(FragmentToSwitchTo.FITNESS_DAY_FRAGMENT)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

}