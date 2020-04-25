package com.example.flipgame.view.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flipgame.R
import com.example.flipgame.model.Number
import kotlinx.android.synthetic.main.grid_item.view.*


class DataAdapter(private val clickListener: ((Int, Int, View) -> Unit)): RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private val listOfInts = mutableListOf<Number>()
    fun addData(list: List<Number>) {
        listOfInts.clear()
        listOfInts.addAll(list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false))
    }

    override fun getItemCount() = listOfInts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listOfInts[position])
        holder.itemView.setOnClickListener {
            if(!listOfInts[position].show!!) {
                clickListener.invoke(listOfInts[position].number!!, position, holder.itemView)
            }
        }
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(movieModel: Number) {
            if(movieModel.show == true) {
                itemView.setBackgroundResource(R.drawable.round_corner_white)
                itemView.item_number.text = movieModel.number.toString()
                itemView.item_number.setTextColor(Color.BLACK)
            } else {
                itemView.setBackgroundResource(R.drawable.round_corner_blue)
                itemView.item_number.text = "?"
                itemView.item_number.setTextColor(Color.WHITE)
            }
        }
    }
}
