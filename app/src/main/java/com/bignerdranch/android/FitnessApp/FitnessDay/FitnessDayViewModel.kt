package com.bignerdranch.android.FitnessApp.FitnessDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import com.bignerdranch.android.FitnessApp.FitnessDay.Repository.FitnessDayRepository
import java.util.*

class FitnessDayViewModel : ViewModel() {
    //prevents from double data extraction from database
    // upon screen rotation

    //Repository instance
    private val fitnessDayRepository = FitnessDayRepository.get()
    private val fitnessDateLiveData = MutableLiveData<Date>()

    //since is public, don't expose MutableLiveData
    var fitnessDayLiveData: LiveData<FitnessDay?> =
        Transformations.switchMap(fitnessDateLiveData) { fitnessDate ->
            fitnessDayRepository.getFitnessDay(fitnessDate)
        }

    val fitnessDayLiveDataList = fitnessDayRepository.getFitnessDays()

    fun checkIfExistsIfNotAdd(fitnessDay: FitnessDay) {
        //adds entry on separate thread
        fitnessDayRepository.checkIfExistsAndAdd(fitnessDay)
        loadFitnessDay(fitnessDay.date)
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