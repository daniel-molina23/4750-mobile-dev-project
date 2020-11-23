package com.bignerdranch.android.FitnessApp

/**
 * File: Person.kt
 * Goal: To be used to model the person the user defines
 * */

//Using a Data Class so that The compiler automatically creates setters and getters for us
data class Person(var firstName: String,
                  var lastName: String,
                  var sex: String,
                  var weight: Int,
                  var age: Int,
                  var height: Int){}