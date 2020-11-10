package com.bignerdranch.android.FitnessApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.*

//User Defined Integer that is sent to the child activity and then received back by the parent activity
private const val REQUEST_CODE_MENU = 0

class MainActivity : AppCompatActivity() {

    //Will Contain all the info about the person
    private var person: Person = Person("","","",0,0)
    //Used to Reference the Widgets in the layout file
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var sexSpinner: Spinner
    private lateinit var weightInput: EditText
    private lateinit var ageInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isProfileCreated()){
            //if profile Already Created, go to next activity and close this one
            goToNextActivity()
        }


        setContentView(R.layout.sign_in_display)

        //Initializing Data Fields
        firstNameInput = findViewById(R.id.first_name)
        lastNameInput = findViewById(R.id.last_name)
        sexSpinner = findViewById(R.id.sex_spinner)
        weightInput = findViewById(R.id.et_weight)
        ageInput = findViewById(R.id.et_age)


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

            var tempWeight = "" //temporary variable to ensure correct format prior to append to 'weight' var
            var tempAge = ""    //temporary variable to ensure correct format prior to append to 'age' var

            //Saving the Users First and Last Name
            person.firstName = firstNameInput.getText().toString()
            person.lastName = lastNameInput.getText().toString()

            //Getting the Users Sex
            person.sex =  sexSpinner.selectedItem.toString()

            //Getting the Users Weight and Age as well as ensuring correct input
            tempWeight = weightInput.text.toString()
            tempAge = ageInput.text.toString()

            var weightRegex = Regex("[1-9][0-9]{1,2}")

            if (weightRegex.matches(tempWeight)) {
                person.weight = Integer.valueOf(tempWeight)
            }else{
                person.weight = 0 //ensure original value
            }

            var ageRegex = Regex("([1-9][0-9]|1[0-4][0-9]|[0-9])")

            if (ageRegex.matches(tempAge)) {
                person.age = Integer.valueOf(tempAge)
            }else{
                person.age = 0 //ensure original value
            }
            //TODO - make the name, sex, age, and weight to save locally
            //IF we got valid Input
            if(person.firstName != "" && person.lastName != "" && person.sex != "Select Sex" && person.weight != 0 && person.age != 0) {

                //make the ProfileManager Persist through
                ProfileManager.setStoredFirstName(applicationContext, person.firstName)
                ProfileManager.setStoredLastName(applicationContext, person.lastName)
                ProfileManager.setStoredSex(applicationContext, person.sex)
                ProfileManager.setStoredUserWeight(applicationContext, person.weight)
                ProfileManager.setStoredUserAge(applicationContext, person.age)

                //Goes to the next activity since profile was successfully created
               goToNextActivity()
            }
            else {
                Toast.makeText(this, "User profile is required to use the app\n"
                    + "1 or more fields are incorrect!"
                    , Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToNextActivity(){
        //Switching to the other Activity
        val intent = Intent(this@MainActivity, FitnessDayActivity::class.java)
        startActivity(intent)
        finish() //Killing it so that the activity dies after it switches
    }

    private fun isProfileCreated() : Boolean{
        val firstName = ProfileManager.getStoredFirstName(applicationContext)
        val lastName = ProfileManager.getStoredLastName(applicationContext)
        val sex = ProfileManager.getStoredSex(applicationContext)
        val weight = ProfileManager.getStoredUserWeight(applicationContext)
        val age = ProfileManager.getStoredUserAge(applicationContext)

        return (firstName != "" && lastName != "" && sex != "Select Sex" && weight != 0 && age != 0)
    }
}