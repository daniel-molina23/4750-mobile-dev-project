package com.bignerdranch.android.FitnessApp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class FitnessDay(@PrimaryKey val id: UUID = UUID.randomUUID(),
                      @PrimaryKey var date: Date = Date(),
                      var calories: Int = 0,
                      var exerciseIsAdded: Boolean = false,
                      var foodIsAdded: Boolean = false){}