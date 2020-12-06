package com.bignerdranch.android.FitnessApp.FitnessDay.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import com.bignerdranch.android.FitnessApp.database.FitnessDayDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "FitnessDayRepository"
private const val DATABASE_NAME = "fitnessday-database"

class FitnessDayRepository private constructor(context: Context) {

    private val database: FitnessDayDatabase = Room.databaseBuilder(
        context.applicationContext,
        FitnessDayDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val fitnessDao = database.fitnessDayDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getFitnessDays(): LiveData<List<FitnessDay>> = fitnessDao.getFitnessDays()

    fun getFitnessDay(date: Date): LiveData<FitnessDay?> = fitnessDao.getFitnessDay(date)

    fun updateFitnessDay(fitnessDay: FitnessDay){
        executor.execute {
            fitnessDao.updateFitnessDay(fitnessDay)
        }
    }

    fun checkIfExistsAndAdd(fitnessDay: FitnessDay){
        executor.execute{
            val dateNotExists = fitnessDao.exists(fitnessDay.date) == 1
            if(dateNotExists) {
                Log.i(TAG, "***Trying to add a new Date to Database***")
                fitnessDao.addFitnessDay(fitnessDay)
            }else{
                Log.i(TAG, "***Already in Database!***")
            }
        }
    }

    //Adds a FitnessDay to the Database On a background thread
    fun addFitnessDay(fitnessDay: FitnessDay){
        executor.execute {
            fitnessDao.addFitnessDay(fitnessDay)
        }
    }

    companion object{
        private var INSTANCE: FitnessDayRepository? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = FitnessDayRepository(context)
            }
        }

        fun get(): FitnessDayRepository {
            return INSTANCE ?: throw IllegalStateException("FitnessRepository must be initialized")
        }
    }
}