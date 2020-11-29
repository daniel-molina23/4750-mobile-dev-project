package com.bignerdranch.android.FitnessApp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.FitnessApp.FitnessDay.data.CustomExerciseHashMap
import com.bignerdranch.android.FitnessApp.FitnessDay.data.CustomFoodHashMap
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "EEE MMM dd 00:00:00 yyyy"

class FitnessDayTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?) : String?{
        val formatter = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        if(date==null){
            val newDate = Date()
            return formatter.format(newDate)
        }
        //else: NOT NULL
        return formatter.format(date)
    }

    @TypeConverter
    fun toDate(dateString: String): Date?{
        //string MUST BE PRESENT!!!!
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        return sdf.parse(dateString) as Date
    }

    @TypeConverter
    fun fromCustomFoodHashMap(cHMapFood: CustomFoodHashMap?) : String?
    {
        return cHMapFood?.toString()
    }

    @TypeConverter
    fun toCustomFoodHashMap(customHashMapString: String?) : CustomFoodHashMap?{
        return customHashMapString?.let{
            CustomFoodHashMap(it)
        }
    }

    @TypeConverter
    fun fromCustomExerciseHashMap(cHMapFood: CustomExerciseHashMap?) : String?
    {
        return cHMapFood?.toString()
    }

    @TypeConverter
    fun toCustomExerciseHashMap(customHashMapString: String?) : CustomExerciseHashMap? {
        return customHashMapString?.let{
            CustomExerciseHashMap(it)
        }
    }
}