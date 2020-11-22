package com.bignerdranch.android.FitnessApp

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


private const val TAG = "FitnessListFragment"

class FitnessListFragment : Fragment() {
    //interface for hosting activities
    interface Callbacks{
        fun changeFitnessDayFragment(date: Date) //TODO - implement database to save fields
    }

    private var callbacks: Callbacks? = null

    private lateinit var fitnessDayRecyclerView: RecyclerView

    // Added to create the livedata observer under onViewCreated
    private val fitnessViewModel: FitnessDayViewModel by lazy {
        ViewModelProvider(this).get(FitnessDayViewModel::class.java)
    }
    //adapter added for the class
    private var adapter : FitnessDayAdapter? = FitnessDayAdapter(emptyList())


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

        val view = inflater.inflate(R.layout.fitness_list_fragment, container, false)

        fitnessDayRecyclerView =
            view.findViewById(R.id.days_list_recycler_view) as RecyclerView

        fitnessDayRecyclerView.layoutManager = LinearLayoutManager(context)

        //TODO - plug adapter here for the fitnessListViewModel (class not yet created)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //add a crimeListViewModel.LiveDataObserver
        fitnessViewModel.fitnessDayLiveData.observe(
            viewLifecycleOwner,
            Observer{ fitnessDays ->
                fitnessDays?.let {
                    Log.i(TAG, "Fitness days have been imported");
                    updateUI(fitnessDays)
                }
            }
        )
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

            dateTextView.text = fitnessDay.date.toString() //Date instance

            calorieTextView.text = fitnessDay.foodCalories.toString() //numeric value

            //Depending if they have inputted any foods then we will show the image
            foodAddedImageView.visibility = if (fitnessDay.foodCalories.computeTotalCalories() > 0) {
                View.VISIBLE
            } else{
                View.GONE
            }

            exerciseAddedImageView.visibility = if (fitnessDay.exerciseCalories.computeTotalCalories() > 0)
            {
                View.VISIBLE
            }
            else
            {
                View.GONE
            }
        }

        override fun onClick(v: View) {
            //call interface callback!
            callbacks?.changeFitnessDayFragment(fitnessDay.date)
        }
    }

    private fun updateUI(fitnessDays: List<FitnessDay>){
        adapter = FitnessDayAdapter(fitnessDays)
        fitnessDayRecyclerView.adapter = adapter
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