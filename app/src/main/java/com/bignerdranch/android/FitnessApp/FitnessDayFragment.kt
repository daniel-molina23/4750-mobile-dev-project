package com.bignerdranch.android.FitnessApp

import android.view.*
import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val FITNESS_ID = "fitness_id"
private const val ARG_FITNESS_DATE = "fitness_date"

class FitnessDayFragment : Fragment() {

    //Defining Variables that will be associated with the widgets
    private lateinit var fitnessDay: FitnessDay
    private lateinit var addFoodButton: Button
    private lateinit var addExerciseButton: Button
    private lateinit var notesTextView: EditText
    private lateinit var dateButton: Button
    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fitnessDay = FitnessDay()
        val time = arguments?.getString(ARG_FITNESS_DATE) as Long
        val fitnessDate : Date = Date(time)

        fitnessViewModel.loadFitnessDay(fitnessDate)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu_fragment, container, false)

        //Initializing the Widgets
        addFoodButton = view.findViewById(R.id.add_food)
        addExerciseButton = view.findViewById(R.id.add_exercise)
        notesTextView = view.findViewById(R.id.notes_for_day)
        dateButton = view.findViewById(R.id.display_and_change_date_button)
        //TODO - Adding Code Which Edits the Notes
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

    fun updateUI()
    {
        dateButton.text = fitnessDay.date.toString()
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

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

        notesTextView.addTextChangedListener(titleWatcher)

        addExerciseButton.setOnClickListener{
            Toast.makeText(context, "Add Exercise is working!!!", Toast.LENGTH_SHORT).show()
        }

        addFoodButton.setOnClickListener{
            Toast.makeText(context, "Add Food is working!!!", Toast.LENGTH_SHORT).show()
        }

        dateButton.setOnClickListener {
            Toast.makeText(context, "Date Button is working!!", Toast.LENGTH_SHORT).show()
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