package com.bignerdranch.android.FitnessApp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.FitnessApp.FitnessDay.data.CustomExerciseHashMap
import com.bignerdranch.android.FitnessApp.FitnessDay.data.CustomFoodHashMap
import java.util.*

class FitnessDayTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?) : Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date?{
        return millisSinceEpoch?.let{
            Date(it)
        }
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