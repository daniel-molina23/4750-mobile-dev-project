package com.bignerdranch.android.FitnessApp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class FitnessDay(
                      @PrimaryKey var date: Date = Date(),
                      var notesText: String = "",
                      var foodCalories: CustomFoodHashMap = CustomFoodHashMap(),
                      var exerciseCalories: CustomExerciseHashMap = CustomExerciseHashMap()){}