package fr.esimed.todo

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.todo.data.dao.TaskDAO
import android.view.LayoutInflater
import fr.esimed.todo.data.model.Category

class TaskAdapter(private val context: Context, private val taskDAO: TaskDAO, private val category: Category): RecyclerView.Adapter<TaskViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder
    {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.listrow, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int)
    {
        val id = category.id ?: return
        val task = taskDAO.getTaskByPositionCategory(id, position) ?: return
        holder.textTask.text = task.toString()
        holder.imgDone.setImageDrawable(if (task.done) context.resources.getDrawable(R.drawable.ok) else context.resources.getDrawable(R.drawable.notok))

        holder.textTask.setOnClickListener {
            task.done = !task.done
            taskDAO.update(task)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int
    {
        val idCategory = category.id ?: return 0
        return taskDAO.getNbTaskByCategory(idCategory)
    }

}