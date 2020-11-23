package com.bignerdranch.android.FitnessApp

/**
 * Used So that Fragments can pass data to activities
 *
 * - Connect the Containing Class Implementation of the itnerface to the fragment in the onAttach Method (YourFragment)
 * - The Activity will Implement This Interface
 * https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
 * */
interface OnDataPass
{
    fun onDataPass(data: FragmentToSwitchTo)
}