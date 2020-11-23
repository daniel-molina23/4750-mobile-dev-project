package com.bignerdranch.android.FitnessApp.FitnessDay.data

class CustomExerciseHashMap() {

    private lateinit var hashMap: HashMap<String, Int>

    init {
        this.hashMap = HashMap(2)
        this.hashMap["weight"] = 0
        this.hashMap["cardio"] = 0
    }

    constructor(parsableStringForDatabase : String) : this(){
        val lstValues: List<String> = parsableStringForDatabase.split(",")
        lstValues.forEach { it ->
            var keyValuePair = it.split("/")
            this.hashMap[keyValuePair[0]] = keyValuePair[1].toInt()
        }
    }


    fun getValue(key: String) : Int
    {
        if(this.hashMap[key.toLowerCase()] == null)
        {
            return 0
        }
        return this.hashMap[key.toLowerCase()]!!
    }

    fun setValue(key: String, value : Int)
    {
        this.hashMap[key.toLowerCase()] = value
    }

    override fun toString() : String{
        return "weight/" + this.hashMap["weight"] + ",cardio/" + this.hashMap["cardio"]
    }

    /**Computes all the values  */

    /**Loop through all the present keys value pairs and adds the values*/
    fun computeTotalCalories() : Int
    {
        var answer: Int = 0
        for((key, value) in this.hashMap)
        {
            answer += value
        }

        return answer
    }
}