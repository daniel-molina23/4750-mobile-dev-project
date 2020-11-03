package com.bignerdranch.android.FitnessApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // not making class because one one person
        var name = ""
        var sex = ""
        var tempWeight = "" //temporary variable to ensure correct format prior to append to 'weight' var
        var weight = 0
        var tempAge = ""    //temporary variable to ensure correct format prior to append to 'age' var
        var age = 0


        var nameInput : EditText = findViewById(R.id.et_name)
        var sexSpinner : Spinner = findViewById(R.id.sex_spinner)
        var weightInput : EditText  = findViewById(R.id.et_weight)
        var ageInput : EditText = findViewById(R.id.et_age)

        //adapter for the spinner to respond to selection
        ArrayAdapter.createFromResource(
            this,
            R.array.sex_array,
            android.R.layout.simple_spinner_item,
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexSpinner.adapter = adapter
        }

        var createProfile : Button = findViewById(R.id.createProfile)
        createProfile.setOnClickListener {


            name = nameInput.getText().toString()
            sex = sexSpinner.selectedItem.toString()
            tempWeight = weightInput.text.toString()
            tempAge = ageInput.text.toString()

            var weightRegex = Regex("[1-9][0-9]{1,2}")

            if (weightRegex.matches(tempWeight)) {
                weight = Integer.valueOf(tempWeight)
            }else{
                weight = 0 //ensure original value
            }

            var ageRegex = Regex("([1-9][0-9]|1[0-4][0-9]|[0-9])")

            if (ageRegex.matches(tempAge)) {
                age = Integer.valueOf(tempAge)
            }else{
                age = 0 //ensure original value
            }

            //TODO - make the name, sex, age, and weight to save locally
            if(name != "" && sex != "Select Sex" && weight != 0 && age != 0) {

                Toast.makeText(this, "Name, $name\n" +
                        " Sex, $sex\n" +
                        "Weight, $weight\n" +
                        "Age, $age", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                Toast.makeText(this, "User profile is required to use the app\n"
                    + "1 or more fields are incorrect!"
                    , Toast.LENGTH_SHORT).show()
            }


        }
    }
}