package com.example.quiz2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : StationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        val fourDigitViewModelFactory = StationViewModelFactory(getApplicationContext())
        viewModel = ViewModelProvider(this, fourDigitViewModelFactory).get(StationViewModel::class.java)

        // Getting the elements from the view
        val recyclerView : RecyclerView = findViewById(R.id.recyclerViewStations)
        val fab : FloatingActionButton = findViewById(R.id.fabAdd)

        viewModel.stationList.observe(this, Observer { stations ->
            var viewAdapter : ListAdapter = ListAdapter(stations)
            recyclerView.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
        })

        fab.setOnClickListener {
            val intent = Intent(this, AddStationActivity::class.java)
            startActivity(intent)
        }
    }


}
