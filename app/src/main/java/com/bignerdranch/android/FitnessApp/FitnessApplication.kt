package com.bignerdranch.android.FitnessApp

import android.app.Application

class FitnessApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FitnessDayRepository.initialize(this)
    }
}