package com.bignerdranch.android.FitnessApp.FitnessDay.ExerciseAndFood

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bignerdranch.android.FitnessApp.FitnessDay.data.CustomExerciseHashMap
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import com.bignerdranch.android.FitnessApp.FitnessDay.Repository.FitnessDayRepository
import com.bignerdranch.android.FitnessApp.FitnessDay.FragmentToSwitchTo
import com.bignerdranch.android.FitnessApp.FitnessDay.OnDataPass
import com.bignerdranch.android.FitnessApp.R

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

    //in order to validate the inputs made by the user!
    // from 0 calories - 30,000 calories (world record is 19,000 calories burned in 1 day)
    private val numericRegex = Regex("[0-9]|[1-9][0-9]{1,3}|[1-2][0-9]{1,4}|(30000)")

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

        return view
    }

    override fun onStart() {
        super.onStart()

        if(fitnessDay.exerciseCalories.computeTotalCalories() != 0) {
            if (fitnessDay.exerciseCalories.getValue("weight") != 0)
                weightTrainEditText.setText(fitnessDay.exerciseCalories.getValue("weight").toString())
            if (fitnessDay.exerciseCalories.getValue("cardio") != 0)
                cardioEditText.setText(fitnessDay.exerciseCalories.getValue("cardio").toString())
        }


        //Switching Back to the FitnessDayFragment
        this.mainMenuButton.setOnClickListener { view ->
            if(numericRegex.matches(cardioEditText.text) &&
                numericRegex.matches(weightTrainEditText.text)){

                this.passData(FragmentToSwitchTo.FITNESS_DAY_FRAGMENT, fitnessDay)
            }
            else{
                Toast.makeText(context, "Please Enter Valid Data\n"
                        + "1 or more fields are incorrect!"
                    , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun ensureProperCalorieInput(){
        var invalidCount = 0
        cardioText = if(numericRegex.matches(cardioEditText.text)){
            cardioEditText.text.toString()
        } else{
            invalidCount ++
            "0"
        }

        weightText = if(numericRegex.matches(weightTrainEditText.text)){
            weightTrainEditText.text.toString()
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
        val exerciseHashMap: CustomExerciseHashMap =
            CustomExerciseHashMap("weight/" + weightText +
                    ",cardio/" + cardioText)

        fitnessDay.exerciseCalories = exerciseHashMap

        fitnessDayRepo.updateFitnessDay(fitnessDay)
    }
}