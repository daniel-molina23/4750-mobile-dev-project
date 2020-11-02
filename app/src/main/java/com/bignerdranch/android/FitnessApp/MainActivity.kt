package com.bignerdranch.android.FitnessApp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // not making class because one one person
        var name = ""
        var sex = ""
        var tempWeight = ""
        var weight = 0
        var tempAge = ""
        var age = 0


        var nameInput : EditText = findViewById(R.id.et_name)
        var sexInput : EditText = findViewById(R.id.et_sex)
        var weightInput : EditText  = findViewById(R.id.et_weight)
        var ageInput : EditText = findViewById(R.id.et_age)


        var createProfile : Button = findViewById(R.id.createProfile)
        createProfile.setOnClickListener {


            name = nameInput.getText().toString()
            sex = sexInput.getText().toString()
            tempWeight = weightInput.getText().toString()
            tempAge = ageInput.text.toString()

            var weightRegex = Regex("[1-9][0-9]{1,2}")

            if (weightRegex.matches(tempWeight)) {
                weight = Integer.valueOf(tempWeight)
            }

            var ageRegex = Regex("([1-9][0-9]|1[0-4][0-9]|[0-9])")

            if (ageRegex.matches(tempAge)) {
                age = Integer.valueOf(tempAge)
            }


            //TODO fix variable reset if wrong info
            if(name != "" && sex != "" && weight != 0 && age != 0) {

                Toast.makeText(this, "Name, $name\n" +
                        " Sex, $sex\n" +
                        "Weight, $weight\n" +
                        "Age, $age", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "User profile is required to use the app"
                    , Toast.LENGTH_SHORT).show()
            }


        }
    }
}