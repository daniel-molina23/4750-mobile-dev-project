package com.bignerdranch.android.FitnessApp

import android.view.*
import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val FITNESS_ID = "fitness_id"
private const val ARG_FITNESS_DATE = "fitness_date"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0
private const val DATE_FORMAT = "EEEE MMM. d, yyyy"
private const val TAG = "FitnessDayFragment"

/**
 * Main Menu Screen
 * */

class FitnessDayFragment : Fragment(), DatePickerFragment.Callbacks {

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
    private var foodCalorieCount: Int = 0
    private lateinit var addExerciseButton: Button
    private var exerciseCalorieCount: Int = 0
    private lateinit var notesEditText: EditText
    private lateinit var dateButton: Button
    private lateinit var progressBar: ProgressBar
    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
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

        //Initializing the Widgets
        addFoodButton = view.findViewById(R.id.add_food) as Button
        addExerciseButton = view.findViewById(R.id.add_exercise) as Button
        notesEditText = view.findViewById(R.id.notes_for_day) as EditText
        dateButton = view.findViewById(R.id.display_and_change_date_button) as Button
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar
        progressBar.max = 2

        //Compute the total calories and populate the progress bar!!
        foodCalorieCount = fitnessDay.foodCalories.computeTotalCalories()
        exerciseCalorieCount = fitnessDay.exerciseCalories.computeTotalCalories()

        //initialize to today's date for seamless feel
        dateButton.text = getDateFormatString(fitnessDay.date)

        return view
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

    override fun onStart() {
        super.onStart()

        val notesWatcher = object : TextWatcher {

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
                // Updates fitnessDay notes section after text has changed
                fitnessViewModel.saveFitnessDay(fitnessDay);
            }

            override fun afterTextChanged(sequence: Editable?) {
                //This one too
            }
        }

        notesEditText.addTextChangedListener(notesWatcher)

        addExerciseButton.setOnClickListener {
            Toast.makeText(context, "Add Exercise is working!!!", Toast.LENGTH_SHORT).show()
        }

        addFoodButton.setOnClickListener {
            Toast.makeText(context, "Add Food is working!!!", Toast.LENGTH_SHORT).show()
        }

        dateButton.setOnClickListener {
            //then use the DatePickerFragment
            DatePickerFragment.newInstance(fitnessDay.date).apply{
                //sending data to the target fragment, connection
                setTargetFragment(this@FitnessDayFragment,
                    REQUEST_DATE)
                show(this@FitnessDayFragment.parentFragmentManager,
                    DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        fitnessViewModel.saveFitnessDay(fitnessDay)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.calendar_dropdown -> {
                Toast.makeText(context, "Calendar Drop Down!"
                    , Toast.LENGTH_SHORT).show()
            }
            R.id.profile_dropdown -> {
                Toast.makeText(context, "Profile Drop Down!"
                    , Toast.LENGTH_SHORT).show()
            }
            R.id.list_view_dropdown -> {
                Toast.makeText(context, "List View Drop Down!"
                    , Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, FitnessListActivity::class.java)
                startActivity(intent)
                activity!!.finish() //TODO - ensure "!!" works always....
            }
        }
        return true
    }

    override fun onDateSelected(date: Date) {
        //if we select a different date than is present,
        //      use callbacks to synchronously change fragments
        if(fitnessDay.date != date){
            Log.d(TAG, "different date identified!!")

            //save the current app's data as is before switching dates!
            Log.d(TAG, "Saved the current date before switching to DatePicker!")
            fitnessViewModel.saveFitnessDay(fitnessDay)

            //date NOT present, add to DB!!!
            if(!fitnessViewModel.isDatePresent(date)){
                //only create new table date row for database
                //   if not present
                val newFitnessDay = FitnessDay()
                newFitnessDay.date = date
                Log.d(TAG, "we saved new date to DB")
                fitnessViewModel.addFitnessDay(newFitnessDay)
                //set up the new fitnessDay
                fitnessDay = newFitnessDay
            }
            else{ //date is present in DB
                fitnessDay = fitnessViewModel.getFitnessDayAtDate(date)
            }
            updateUI()
            callbacks?.changeFitnessDayFragment(date)
        }
        //else keep it as is!
    }

    private fun getDateFormatString(date: Date) : String{
        return DateFormat.format(DATE_FORMAT, date).toString()
    }

    private fun updateUI(){
        dateButton.text = getDateFormatString(fitnessDay.date)
        notesEditText.setText(fitnessDay.notesText)
        foodCalorieCount = fitnessDay.foodCalories.computeTotalCalories()
        exerciseCalorieCount = fitnessDay.exerciseCalories.computeTotalCalories()
        //change the progress bar look!
        progressBar.progress = if(foodCalorieCount != 0 && exerciseCalorieCount != 0){
            2
        } else if (foodCalorieCount == 0 && exerciseCalorieCount == 0){
            0
        } else{
            1
        }
    }

    //Static method
    companion object {
        fun newInstance(date: Date) : FitnessDayFragment{

            val args = Bundle().apply{
                putLong(ARG_FITNESS_DATE, date.time)
            }

            return FitnessDayFragment().apply{
                arguments = args
            }
        }
    }
}