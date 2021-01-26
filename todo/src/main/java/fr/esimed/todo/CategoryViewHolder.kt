package fr.esimed.todo

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryViewHolder(row:View): RecyclerView.ViewHolder(row)
{
    val titleCategory = row.findViewById<TextView>(R.id.nameCategory)
    val btnUpdate = row.findViewById<ImageButton>(R.id.buttonEditCategory)
    val btnDelete = row.findViewById<ImageButton>(R.id.buttonDeleteCategory)
    val nbTask = row.findViewById<TextView>(R.id.count_task)
}