package com.bignerdranch.android.FitnessApp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.FitnessApp.FitnessDay

@Database(entities = [FitnessDay::class], version = 1)
@TypeConverters(FitnessDayTypeConverters::class)
abstract class FitnessDayDatabase : RoomDatabase() {

    abstract fun fitnessDayDao(): FitnessDayDao
}