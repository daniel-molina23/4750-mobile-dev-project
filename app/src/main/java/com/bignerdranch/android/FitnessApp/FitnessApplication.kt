package com.bignerdranch.android.FitnessApp

import android.app.Application
import com.bignerdranch.android.FitnessApp.FitnessDay.Repository.FitnessDayRepository

class FitnessApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FitnessDayRepository.initialize(this)
    }
}