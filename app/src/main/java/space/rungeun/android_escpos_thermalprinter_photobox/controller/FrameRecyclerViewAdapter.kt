package space.rungeun.android_escpos_thermalprinter_photobox.controller

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.rungeun.android_escpos_thermalprinter_photobox.R

class FrameRecyclerViewAdapter(val maxValue: Int) : RecyclerView.Adapter<MyViewHolder>() {

    private var isShootMode = false
    private val photos = mutableMapOf<Int, Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.list_frame, parent, false)
        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return maxValue
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textItem.text = "${position + 1}"

        if (isShootMode) {
            // 사진이 있으면 ImageView에 표시
            photos[position]?.let { bitmap ->
                holder.imageView?.setImageBitmap(bitmap)
                holder.imageView?.visibility = View.VISIBLE
                holder.textItem.visibility = View.GONE
            } ?: run {
                // 사진이 없으면 빈 프레임
                holder.imageView?.visibility = View.INVISIBLE
                holder.textItem.visibility = View.VISIBLE
            }
        } else {
            holder.textItem.visibility = View.VISIBLE
            holder.imageView?.visibility = View.GONE
        }
    }

    fun shootMode() {
        isShootMode = true
        notifyDataSetChanged()
    }

    // 이름 변경: addPhoto → updatePhoto
    fun updatePhoto(bitmap: Bitmap, position: Int) {
        photos[position] = bitmap
        notifyItemChanged(position)
    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textItem: TextView = view.findViewById(R.id.textItem)
    val imageView: ImageView? = view.findViewById(R.id.imageItem)
}