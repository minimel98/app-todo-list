package fr.esimed.todo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class TaskViewHolder(row:View): RecyclerView.ViewHolder(row)
{
    val imgDone = row.findViewById<ImageView>(R.id.imgListRow)
    val textTask = row.findViewById<TextView>(R.id.taskList)
}