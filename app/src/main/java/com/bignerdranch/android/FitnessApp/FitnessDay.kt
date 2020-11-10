package com.bignerdranch.android.FitnessApp

import java.util.*

data class FitnessDay(val id: UUID = UUID.randomUUID(),
                      var date: Date = Date(),
                      var calories: Int = 0,
                      var exerciseIsAdded: Boolean = false,
                      var foodIsAdded: Boolean = false){}