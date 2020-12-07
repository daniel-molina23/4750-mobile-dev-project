package com.bignerdranch.android.FitnessApp.FitnessList

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import androidx.lifecycle.Observer
import com.bignerdranch.android.FitnessApp.FitnessDay.FitnessDayViewModel
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import com.bignerdranch.android.FitnessApp.Profile.ProfileManager
import com.bignerdranch.android.FitnessApp.R
import java.text.SimpleDateFormat


private const val TAG = "FitnessListFragment"
private const val DATE_FORMAT = "mm/dd/yyyy"


/**
 * This Fragment Is Responsible For Displaying All the FitnessDays in the Data base in a Recycler View
 * */

class FitnessListFragment : Fragment() {
    //interface for hosting activities
    interface Callbacks{
        fun changeFitnessDayFragment(date: Date) //TODO - implement database to save fields
    }
    //date formatter
    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)

    private var callbacks: Callbacks? = null

    //Recycler View Which Is Used to Hold all the FitnessDay Items
    private lateinit var fitnessDayRecyclerView: RecyclerView

    //adapter added for the class
    private var adapter : FitnessDayAdapter? = FitnessDayAdapter(emptyList())

    // Added to create the live data observer under onViewCreated
    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //stash the context
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Associating the Layout for this Fragment
        val view = inflater.inflate(R.layout.fitness_list_fragment, container, false)

        //Finding the Recycler View
        fitnessDayRecyclerView =
            view.findViewById(R.id.days_list_recycler_view) as RecyclerView

        //Every Recycler View Needs A LayoutManager As Soon as its Created
        //The layout manager positions every item and also defines how scrolling works
        fitnessDayRecyclerView.layoutManager = LinearLayoutManager(context)

        //hooking up the adapter to see the holders or list of items
        fitnessDayRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //add a crimeListViewModel.LiveDataObserver
        fitnessViewModel.fitnessDayLiveDataList.observe(
            viewLifecycleOwner,
            Observer{ fitnessDays ->
                fitnessDays?.let {
                    Log.i(TAG, "Fitness days have been imported. Days = ${fitnessDays.size}")
                    updateUI(fitnessDays)
                }
            }
        )
    }

    private fun updateUI(fitnessDays: List<FitnessDay>){
        adapter = FitnessDayAdapter(fitnessDays)
        fitnessDayRecyclerView.adapter = adapter
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class FitnessDayHolder(view : View) :RecyclerView.ViewHolder(view),
            View.OnClickListener{
        private lateinit var fitnessDay: FitnessDay

        private val dateTextView : TextView = itemView.findViewById(R.id.fitness_date)

        private val calorieTextView : TextView = itemView.findViewById(R.id.fitness_calories)

        private val foodAddedImageView : ImageView = itemView.findViewById(R.id.fitness_food_check)

        private val exerciseAddedImageView : ImageView = itemView.findViewById(R.id.fitness_exercise_check)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(fitnessDay: FitnessDay){
            this.fitnessDay = fitnessDay

            //calculate the remaining calories (BMR - FOOD + EXERCISE)
            var remainingCalories : Int = (ProfileManager.getBMR(context!!) - fitnessDay.foodCalories.computeTotalCalories() + fitnessDay.exerciseCalories.computeTotalCalories())

            calorieTextView.text = remainingCalories.toString() //numeric value

            //This needs to be in the format xx/xx/xxxx
            dateTextView.text = dateFormatter.format(fitnessDay.date) //Date instance

            //Depending if they have inputted any foods then we will show the image
            //Should be fixed with database fix
            foodAddedImageView.visibility = if (fitnessDay.foodCalories.computeTotalCalories() > 0) {
                View.VISIBLE
            } else{
                View.GONE
            }

            exerciseAddedImageView.visibility = if (fitnessDay.exerciseCalories.computeTotalCalories() > 0)
            {
                View.VISIBLE
            }
            else {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            //call interface callback!
            callbacks?.changeFitnessDayFragment(fitnessDay.date)
        }
    }

    private inner class FitnessDayAdapter(var fitnessDaysList : List<FitnessDay>)
        : RecyclerView.Adapter<FitnessDayHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessDayHolder {
            val view = layoutInflater.inflate(R.layout.list_item_fitness_days, parent, false)

            return FitnessDayHolder(view)
        }

        override fun onBindViewHolder(holder: FitnessDayHolder, position: Int) {
            val fitnessDay = fitnessDaysList[position]
            holder.bind(fitnessDay)
        }

        //so recycler view knows the number of items to display
        override fun getItemCount() = fitnessDaysList.size
    }

    //static reference
    companion object{
        //gets new fragment instance singleton
        fun newInstance() : FitnessListFragment {
            return FitnessListFragment()
        }
    }
}