package space.rungeun.android_escpos_thermalprinter_photobox.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.rungeun.android_escpos_thermalprinter_photobox.R

class FrameRecyclerViewAdapter : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_frame, parent, false)
        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textItem.text = "$position"
    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textItem: TextView = view.findViewById(R.id.textItem)
}