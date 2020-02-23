package com.example.quiz2

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class AddStationActivity : AppCompatActivity() {

    private lateinit var viewModel : StationViewModel
    lateinit var line_name : String
    lateinit var station_code : String
    lateinit var station_name : String

    private val STORAGE_STATION_CODE : String = "STATION_CODE"
    private val STORAGE_STATION_NAME : String = "STATION_NAME"
    private val STORAGE_LOADED : String = "LOADED"
    private val STORAGE_LINE_NAME : String = "LINE_NAME"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_station)


        val stationViewModelFactory : StationViewModelFactory = StationViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, stationViewModelFactory).get(StationViewModel::class.java)

        // Getting all the views
        val line_name_array = arrayOf("North South Line", "North East Line", "East West Line", "Circle Line", "Downtown Line")

        val spinner = findViewById<Spinner>(R.id.spinnerLineName)
        val station_code_tv = findViewById<EditText>(R.id.editTextStationCode)
        val station_name_tv = findViewById<EditText>(R.id.editTextStationName)


        val add_btn : Button = findViewById(R.id.buttonAdd)
        val clear_btn : Button = findViewById(R.id.buttonClear)

//        var loaded : Boolean? = savedInstanceState?.getBoolean(STORAGE_LOADED)
//        if(loaded == true){
//            station_name_tv.setText(station_name)
//            station_code_tv.setText(station_code)
//        }

        val preferences = getSharedPreferences("PREFS", 0)
        var pref_station_code = preferences.getString("STATION_CODE", "") // Attempts to get from local storage.
        var pref_station_name = preferences.getString("STATION_NAME", "") // Attempts to get from local storage.

        if(pref_station_code != "" && pref_station_name != ""){
            station_name_tv.setText(pref_station_name)
            station_code_tv.setText(pref_station_code)
        }


        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, line_name_array)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // I dont need this
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                line_name = line_name_array[position]
            }
        }

        add_btn.setOnClickListener {
            station_code = station_code_tv.text.toString()
            station_name = station_name_tv.text.toString()
            if(isInputValid(station_code, station_name, line_name))
            {

                val stationObj : StationEntity = StationEntity(station_code, station_name, line_name)
                val add = viewModel.add(stationObj)

                //Save shared Preference
                val editor : SharedPreferences.Editor = preferences.edit()
                editor.putString("STATION_CODE", station_code)  // Saves into local storage.
                editor.putString("STATION_NAME", station_name)  // Saves into local storage.
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.stationList.observe(this, Observer {stations ->
            if(stations.size > 0) {
                Log.d("Size", "size is " + stations.size.toString())
                for( x in stations.indices)
                    Log.d("Hello", stations[x].stationName)
            }
        })
    }

   fun isInputValid(station_code : String , station_name : String, line_name : String): Boolean{
       if(station_code.equals("") || station_name.equals("") || line_name.equals("")) return false
       for(char in station_name){
           if (char.isDigit()) return false
       }
       return true
   }



    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
