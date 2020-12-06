package com.bignerdranch.android.FitnessApp.database

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.FitnessApp.FitnessDay.data.FitnessDay
import java.util.*

@Dao
interface FitnessDayDao {

    /**Getting all Of the FitnessDays In the Data base*/
    @Query("SELECT * FROM FitnessDay")
    fun getFitnessDays(): LiveData<List<FitnessDay>>


    /**Getting a Specific FitnessDay Object Based on the key which is the date */
    @Query("SELECT * FROM FitnessDay WHERE date=(:date)")
    fun getFitnessDay(date: Date): LiveData<FitnessDay?>    //LiveData<null>

    /**determines if a item is in the data base or not based on the boolean value*/
    @Query("SELECT EXISTS (SELECT 1 FROM FitnessDay WHERE date=(:date))")
    fun exists(date: Date): Int

    @Update
    fun updateFitnessDay(fitnessDay: FitnessDay)

    @Insert
    fun addFitnessDay(fitnessDay: FitnessDay)
}