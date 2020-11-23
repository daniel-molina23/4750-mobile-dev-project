package com.bignerdranch.android.FitnessApp.FitnessDay.data

class CustomFoodHashMap()
{
    private lateinit var hashMap: HashMap<String, Int>

    init {
        this.hashMap = HashMap()
        this.hashMap["breakfast"] = 0
        this.hashMap["lunch"] = 0
        this.hashMap["dinner"] = 0
        //this.hashMap["snack"] = 0
    }

    constructor(parsableStringForDatabase : String?) : this(){
        if(parsableStringForDatabase != null) {
            val lstValues: List<String> = parsableStringForDatabase.split(",")
            lstValues.forEach { it ->
                var keyValuePair = it.split("/")
                this.hashMap[keyValuePair[0]] = keyValuePair[1].toInt()
            }
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

    override fun toString() : String {
        return "breakfast/" + this.hashMap["breakfast"] + ",lunch/" + this.hashMap["lunch"] + ",dinner/" + this.hashMap["dinner"]
    }

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