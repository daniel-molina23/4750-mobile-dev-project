package com.bignerdranch.android.FitnessApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class FitnessDayViewModel : ViewModel() {
    //prevents from double data extraction from database
    // upon screen rotation

    //Repository instance
    private val fitnessDayRepository = FitnessDayRepository.get()
    private val fitnessDateLiveData = MutableLiveData<Date>()
    private val fitnessIdLiveData = MutableLiveData<UUID>()

    //since is public, don't expose MutableLiveData
    var fitnessDayLiveData: LiveData<FitnessDay?> =
        Transformations.switchMap(fitnessDateLiveData) { fitnessDate ->
            fitnessDayRepository.getFitnessDay(fitnessDate)
        }

    //Getting the FitnessDay Live Data For the Specified FitnessDay Object
    fun checkIfDatePresent(date: Date) : Boolean {
        return fitnessDayRepository.getFitnessDay(date).value != null //Returning the LiveData
    }

    //Adds a FitnessDay to the Database
    fun addFitnessDay(fitnessDay: FitnessDay){
        fitnessDayRepository.addFitnessDay(fitnessDay)
    }

    fun loadFitnessDay(date: Date){
        fitnessDateLiveData.value = date
    }

    fun saveFitnessDay(fitnessDay: FitnessDay) {
        fitnessDayRepository.updateFitnessDay(fitnessDay)
    }
}