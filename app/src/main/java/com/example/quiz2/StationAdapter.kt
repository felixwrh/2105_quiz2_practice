package com.example.quiz2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ListAdapter(val numberList : List<StationEntity>) : RecyclerView.Adapter<ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false) as View
        return ListViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.station_code.text = numberList[position].stationCode
        holder.station_name.text = numberList[position].stationName
        holder.line_name.text = numberList[position].lineName

    }
}

class ListViewHolder(itemView: View, adapter: ListAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener
{
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    val station_code = itemView.findViewById<TextView>(R.id.textViewStationCode)
    val station_name = itemView.findViewById<TextView>(R.id.textViewStationName)
    val line_name = itemView.findViewById<TextView>(R.id.textViewLineName)
}

