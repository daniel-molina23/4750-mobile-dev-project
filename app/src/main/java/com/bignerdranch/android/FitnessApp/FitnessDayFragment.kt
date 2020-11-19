package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.format.DateFormat
import android.view.*
import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val FITNESS_ID = "fitness_id"
private const val FITNESS_DATE = "fitness_date"

class FitnessDayFragment : Fragment() {

    //Defining Variables that will be associated with the widgets
    private lateinit var fitnessDay: FitnessDay
    private lateinit var addFoodTextView: TextView
    private lateinit var addExerciseTextView: TextView
    private lateinit var notesTextView: EditText
    private lateinit var dateButton: Button
    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fitnessDay = FitnessDay()
        val fitnessDate = arguments?.getSerializable(FITNESS_DATE) as Date

        fitnessViewModel.loadFitnessDay(fitnessDate)

        //TODO - load from the database upon it's selection!
        //Must have a way to check the date... -> Date.setOnClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu_fragment, container, false)

        //Initializing the Widgets
        addFoodTextView = view.findViewById(R.id.add_food)
        addExerciseTextView = view.findViewById(R.id.add_exercise)
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

    fun updateUI(){
        dateButton.text = fitnessDay.date.toString()
        notesTextView
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


    companion object {
        //get a static instance of the fragment!
        fun newInstance(fitnessId: UUID, date: Date) : FitnessDayFragment{
            return FitnessDayFragment()
        }

        fun newInstance(date: Date) : FitnessDayFragment{
            return FitnessDayFragment()
        }
    }
}