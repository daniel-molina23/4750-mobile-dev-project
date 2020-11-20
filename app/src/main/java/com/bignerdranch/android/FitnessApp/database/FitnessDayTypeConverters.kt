package com.bignerdranch.android.FitnessApp.database

import androidx.room.TypeConverter
import com.bignerdranch.android.FitnessApp.CustomExerciseHashMap
import com.bignerdranch.android.FitnessApp.CustomFoodHashMap
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
    fun toUUID(uuid: String?) : UUID?{
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?) : String?{
        return uuid?.toString()
    }

    @TypeConverter
    fun fromCustomFoodHashMap(cHMapFood: CustomFoodHashMap?) : String? {
        return cHMapFood?.parseHashMap()
    }

    @TypeConverter
    fun toCustomFoodHashMap(customHashMapString: String?) : CustomFoodHashMap?{
        return customHashMapString?.let{
            CustomFoodHashMap(it)
        }
    }

    @TypeConverter
    fun fromCustomExerciseHashMap(cHMapFood: CustomExerciseHashMap?) : String? {
        return cHMapFood?.parseHashMap()
    }

    @TypeConverter
    fun toCustomExerciseHashMap(customHashMapString: String?) : CustomExerciseHashMap? {
        return customHashMapString?.let{
            CustomExerciseHashMap(it)
        }
    }
}