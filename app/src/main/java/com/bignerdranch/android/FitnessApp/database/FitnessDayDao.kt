package com.bignerdranch.android.FitnessApp.database

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.FitnessApp.FitnessDay
import java.util.*

@Dao
interface FitnessDayDao {

    @Query("SELECT * FROM fitnessday")
    fun getFitnessDays(): LiveData<List<FitnessDay>>

    @Query("SELECT * FROM FitnessDay WHERE id=(:id)")
    fun getFitnessDay(id: UUID): LiveData<FitnessDay?>

    @Query("SELECT * FROM FitnessDay WHERE date=(:date)")
    fun getFitnessDay(date: Date): LiveData<FitnessDay?>

    @Update
    fun updateFitnessDay(fitnessDay: FitnessDay)

    @Insert
    fun addFitnessDay(fitnessDay: FitnessDay)
}