package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "FitnessListFragment"

class FitnessListFragment : Fragment() {
    //interface for hosting activities
    interface Callbacks{
        fun onFitnessItemSelected(dayId : UUID) //TODO - implement database to save fields
    }

    private var callbacks: Callbacks? = null

    private lateinit var fitnessDayRecyclerView: RecyclerView

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

            calorieTextView.text = fitnessDay.calories.toString() //numeric value

            foodAddedImageView.visibility = if (fitnessDay.foodIsAdded) {
                View.VISIBLE
            } else{
                View.GONE
            }

            exerciseAddedImageView.visibility = if (fitnessDay.exerciseIsAdded) {
                View.VISIBLE
            } else{
                View.GONE
            }
        }

        override fun onClick(p0: View?) {
            callbacks?.onFitnessItemSelected(fitnessDay.id)
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
        //gets new fagment instance singleton
        fun newInstance() : FitnessListFragment {
            return FitnessListFragment()
        }
    }
}