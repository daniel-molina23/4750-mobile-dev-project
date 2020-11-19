package com.bignerdranch.android.FitnessApp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class FitnessDay(@PrimaryKey val id: UUID = UUID.randomUUID(),
                      @PrimaryKey var date: Date = Date(),
                      var notesText: String = "",
                      var foodCalories: CustomHashMap = CustomHashMap(4, true),
                      var exerciseCalories: CustomHashMap = CustomHashMap(2, false)){}