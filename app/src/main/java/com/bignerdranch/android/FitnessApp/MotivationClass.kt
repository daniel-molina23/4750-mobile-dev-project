package com.bignerdranch.android.FitnessApp

import java.util.*

/**Returns a Motivation */

class MotivationClass
{
    private var motivationQuotes = arrayListOf<String>()

    constructor()
    {
        motivationQuotes.add("Your Limitation - It's Only Your Imagination")
        motivationQuotes.add("Push Yourself, Because No One Else Is Going To Do It For You")
        motivationQuotes.add("Sometimes later becomes never. Do It Now")
        motivationQuotes.add("Great Things Never Come From Comfort Zone")
        motivationQuotes.add("Dream It. Wish It . Do It")
        motivationQuotes.add("Success Doesn't Just Find You. You Have To Go Out And Get It")
        motivationQuotes.add("The Harder You Work For Something, The Greater You'll Feel When You Achieve It.")
        motivationQuotes.add("Dream Bigger. Do Bigger")
        motivationQuotes.add("Don't Stop When You're Tired. Stop When You're Done")
        motivationQuotes.add("Wake Up With Determination. Go To Bed With Satisfaction.")
        motivationQuotes.add("Do Something Today That Your Future Self Will Thank You For")
        motivationQuotes.add("Little Things Make Big Days")
    }

    fun getMotivation() : String{

        fun IntRange.random() =
            Random().nextInt((endInclusive + 1) - start) + start

        var randIndex = (0..this.motivationQuotes.size).random() // Getting a Random Quote from the Motivation Quotes

        return this.motivationQuotes[randIndex]
    }

}