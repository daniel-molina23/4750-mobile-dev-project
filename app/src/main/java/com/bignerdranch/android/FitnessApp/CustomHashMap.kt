package com.bignerdranch.android.FitnessApp

class CustomHashMap()
{
    private lateinit var hashMap: HashMap<String, Int>

    constructor(size: Int, isFood: Boolean) : this()
    {
        this.hashMap = HashMap(size)

        if(isFood)
        {
            this.hashMap["breakfast"] = 0;
            this.hashMap["lunch"] = 0;
            this.hashMap["dinner"] = 0;
            this.hashMap["snack"] = 0;
        }
        //Its for exercise
        else
        {
            this.hashMap["weight"] = 0;
            this.hashMap["cardio"] = 0;
        }
    }

    fun getValue(key: String) : Int
    {
        if(this.hashMap[key.toLowerCase()] == null)
        {
            return 0;
        }
        return this.hashMap[key.toLowerCase()]!!
    }

    fun setValue(key: String, value : Int)
    {
        this.hashMap[key.toLowerCase()] = value;
    }


}