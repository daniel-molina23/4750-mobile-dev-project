package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.preference.PreferenceManager

private const val PREF_FIRST_NAME = "userFirstName"
private const val PREF_LAST_NAME = "userLastName"
private const val PREF_USER_SEX = "userSex"
private const val PREF_USER_WEIGHT = "userWeight"
private const val PREF_USER_AGE = "userAge"

object ProfileManager{

    fun setStoredFirstName(context: Context, firstName: String){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_FIRST_NAME, firstName)
            .apply()
    }

    fun getStoredFirstName(context: Context) : String{
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString(PREF_FIRST_NAME, "")!!
    }

    fun setStoredLastName(context: Context, lastName: String){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_LAST_NAME, lastName)
            .apply()
    }

    fun getStoredLastName(context: Context) : String{
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString(PREF_LAST_NAME, "")!!
    }

    fun setStoredSex(context: Context, sex: String){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_USER_SEX, sex)
            .apply()
    }

    fun getStoredSex(context: Context) : String{
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString(PREF_USER_SEX, "Select Sex")!!
    }

    fun setStoredUserWeight(context: Context, weight: Int){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(PREF_USER_WEIGHT, weight)
            .apply()
    }

    fun getStoredUserWeight(context: Context) : Int{
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getInt(PREF_USER_WEIGHT, 0)!!
    }

    fun setStoredUserAge(context: Context, age: Int){
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(PREF_USER_AGE, age)
            .apply()
    }

    fun getStoredUserAge(context: Context) : Int{
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getInt(PREF_USER_AGE, 0)!!
    }
}