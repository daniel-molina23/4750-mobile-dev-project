package com.bignerdranch.android.FitnessApp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


/**
 * Simple Fragment Class Used To Edit the Profile Of the User
 *
 */

/**
 * Things To do
 * - Add User Validation For Inputs
 *
 * */
class ProfileEditorFragment : Fragment() {


    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var sexSpinner: Spinner
    private lateinit var weightInput: EditText
    private lateinit var ageInput: EditText
    private lateinit var saveProfileButton: Button

    //Will Contain all the info about the person
    private var person: Person = Person("","","",0,0,0)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        /**Getting a Reference to the Widgets */

        this.firstNameInput = view.findViewById(R.id.profile_edit_first_name)
        this.lastNameInput = view.findViewById(R.id.profile_edit_last_name)
        this.sexSpinner = view.findViewById(R.id.profile_edit_sex)
        this.weightInput = view.findViewById(R.id.profile_edit_weight)
        this.ageInput = view.findViewById(R.id.profile_edit_age)
        this.saveProfileButton = view.findViewById(R.id.saveProfile)



        val context = activity!!.applicationContext

        //adapter for the spinner to respond to selection
        ArrayAdapter.createFromResource(
            context,
            R.array.sex_array,
            android.R.layout.simple_spinner_item,
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexSpinner.adapter = adapter
        }

        return view
    }

    /**Used to Define Click Listeners
     * And To Initialize the Edit Text
     * */
    override fun onStart() {
        super.onStart()

        /**Initializing it with the original Preferences*/
        val context = activity!!.applicationContext

        this.firstNameInput.setText(ProfileManager.getStoredFirstName(context) + "")
        this.lastNameInput.setText(ProfileManager.getStoredLastName(context) + "" )
        this.weightInput.setText(ProfileManager.getStoredUserWeight(context).toString())
        this.ageInput.setText(ProfileManager.getStoredUserAge(context).toString())

        /**Checking if the Save ProfileButton Was Clicked */
        this.saveProfileButton.setOnClickListener { view ->

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
            //IF we got valid Input
            if(person.firstName != "" && person.lastName != "" && person.sex != "Select Sex" && person.weight != 0 && person.age != 0) {
                //make the ProfileManager Persist through
                ProfileManager.setStoredFirstName(context, person.firstName)
                ProfileManager.setStoredLastName(context, person.lastName)
                ProfileManager.setStoredSex(context, person.sex)
                ProfileManager.setStoredUserWeight(context, person.weight)
                ProfileManager.setStoredUserAge(context, person.age)

                Toast.makeText(context, "Profile Updated\n" , Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Please Enter Valid Data\n"
                        + "1 or more fields are incorrect!"
                    , Toast.LENGTH_SHORT).show()
            }

        }

    }

}