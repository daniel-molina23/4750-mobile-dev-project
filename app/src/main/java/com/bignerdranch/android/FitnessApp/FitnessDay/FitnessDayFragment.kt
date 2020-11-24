package com.bignerdranch.android.FitnessApp.FitnessDay

import android.content.Context
import android.view.*
import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.FitnessApp.*
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import com.bignerdranch.android.FitnessApp.FitnessDay.data.MotivationClass
import com.bignerdranch.android.FitnessApp.FitnessList.FitnessListActivity
import com.bignerdranch.android.FitnessApp.Profile.ProfileManager
import java.util.*

private const val FITNESS_ID = "fitness_id"
private const val ARG_FITNESS_DATE = "fitness_date"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0
private const val DATE_FORMAT = "EEEE MMM. d, yyyy"

/**
 * Main Menu Screen
 * */

class FitnessDayFragment : Fragment(){

    /*
     * Required interface for hosting activities
     */
    interface Callbacks {
        fun changeFitnessDayFragment(date: Date)
    }

    private var callbacks: Callbacks? = null

    //Defining Variables that will be associated with the widgets
    private lateinit var fitnessDay: FitnessDay
    private lateinit var addFoodButton: Button
    private lateinit var foodCalorieCount: TextView
    private lateinit var addExerciseButton: Button
    private lateinit var exerciseCalorieCount: TextView
    private lateinit var remainingCalories: TextView
    private lateinit var notesEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var goalTextView: TextView
    private lateinit var progressTextView: TextView
    private lateinit var motivationTextView: TextView
    private var motivation: MotivationClass = MotivationClass()

    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fitnessDay = FitnessDay()

        //initialize date to current
        val time = arguments?.getLong(ARG_FITNESS_DATE)!!
        val date = Date(time)

        //if date not present, add new day to database
        if(!fitnessViewModel.isDatePresent(date)){
            fitnessViewModel.addFitnessDay(fitnessDay)
        }

        //load the current date
        fitnessViewModel.loadFitnessDay(date)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu_fragment, container, false)

        //connect the options menu
        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))
        setHasOptionsMenu(true)

        //Initializing the Widgets
        addFoodButton = view.findViewById(R.id.add_food) as Button
        addExerciseButton = view.findViewById(R.id.add_exercise) as Button
        notesEditText = view.findViewById(R.id.notes_for_day) as EditText
        dateTextView = view.findViewById(R.id.display_and_change_date_button) as TextView
        foodCalorieCount = view.findViewById(R.id.foodCalEntered) as TextView
        exerciseCalorieCount = view.findViewById(R.id.exerciseCalEntered) as TextView
        remainingCalories = view.findViewById(R.id.remainingCal) as TextView
        goalTextView = view.findViewById(R.id.bmrTotal) as TextView
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar
        progressBar.max = 2

        progressTextView = view.findViewById(R.id.tv_progress) as TextView

        motivationTextView = view.findViewById(R.id.motivation_text_view) as TextView

        //Compute the total calories and populate the progress bar!!
        foodCalorieCount.text = fitnessDay.foodCalories.computeTotalCalories().toString()
        exerciseCalorieCount.text = fitnessDay.exerciseCalories.computeTotalCalories().toString()

        remainingCalories.text = getRemainingCaloriesString()

        //initialize to today's date for seamless feel
        dateTextView.text = getDateFormatString(fitnessDay.date)
        goalTextView.text = ProfileManager.getBMR(context!!).toString()

        //Initializing the Motivation Quote
        this.motivationTextView.setText(motivation.getMotivation())

        return view
    }

    fun getRemainingCaloriesString() : String{
        val food = Integer.valueOf(foodCalorieCount.text.toString())
        val exercise = Integer.valueOf(exerciseCalorieCount.text.toString())
        val finalValue = ProfileManager.getBMR(context!!) - food + exercise
        return finalValue.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fitness_day_nav_bar, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitnessViewModel.fitnessDayLiveData.observe(
            viewLifecycleOwner,
            Observer{fitnessDay ->
                fitnessDay?.let{
                    this.fitnessDay = fitnessDay
                    updateUI()
                }
            }
        )
    }


    /**Function Called When the Fragment Starts*/

    override fun onStart() {
        super.onStart()

        val notesWatcher = object : TextWatcher
        {

            override fun beforeTextChanged(sequence: CharSequence?,
                                           start: Int,
                                           count: Int,
                                           after: Int) {
                //This space intentionally left blank
            }

            override fun onTextChanged(sequence: CharSequence?,
                                       start: Int,
                                       before: Int,
                                       count: Int) {
                fitnessDay.notesText = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //This one too
            }
        }

        notesEditText.addTextChangedListener(notesWatcher)

        /**Called When the User Clicked the add Exercise Button*/
        addExerciseButton.setOnClickListener {

            this.passData(FragmentToSwitchTo.EXERCISE_FRAGMENT, fitnessDay) //Telling the Activity To Switch To the Exercise Fragment
        }

        /**Called When the User Clicks the add Food button */
        addFoodButton.setOnClickListener {
            this.passData(FragmentToSwitchTo.FOOD_FRAGMENT, fitnessDay)  //Telling the Activity To Switch to the Food Fragment
        }

    }

    override fun onStop() {
        super.onStop()
        fitnessViewModel.saveFitnessDay(fitnessDay)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.profile_dropdown -> {
                this.passData(FragmentToSwitchTo.PROFILE_EDITOR_FRAGMENT, FitnessDay()) //Switching to the Profile_editor_fragment
            }

            /**The User Wants A Recycler View List Of all Its FitnessDays */
            R.id.list_view_dropdown -> {
                Toast.makeText(context, "List View Drop Down!"
                    , Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, FitnessListActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    private fun getDateFormatString(date: Date) : String{
        return DateFormat.format(DATE_FORMAT, date).toString()
    }

    private fun updateUI(){
        goalTextView.text = ProfileManager.getBMR(context!!).toString()
        dateTextView.text = getDateFormatString(fitnessDay.date)
        notesEditText.setText(fitnessDay.notesText)
        //Compute the total calories and populate the progress bar!!
        foodCalorieCount.text = fitnessDay.foodCalories.computeTotalCalories().toString()
        exerciseCalorieCount.text = fitnessDay.exerciseCalories.computeTotalCalories().toString()
        remainingCalories.text = getRemainingCaloriesString()
        //change the progress bar look!
        progressBar.progress = if(foodCalorieCount.text != "0" && exerciseCalorieCount.text != "0"){
            2
        } else if (foodCalorieCount.text == "0" && exerciseCalorieCount.text == "0"){
            0
        } else{
            1
        }

        //change the text at the end of the progress bar!
        when(progressBar.progress){
            0 -> progressTextView.text = "0/2"
            1 -> progressTextView.text = "1/2"
            2 -> progressTextView.text = "2/2"
        }
    }

    //Static method
    companion object {
        fun newInstance(date: Date) : FitnessDayFragment {

            val args = Bundle().apply{
                putLong(ARG_FITNESS_DATE, date.time)
            }

            return FitnessDayFragment().apply{
                arguments = args
            }
        }
    }
}