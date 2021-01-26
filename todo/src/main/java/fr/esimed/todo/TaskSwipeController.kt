package fr.esimed.todo

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.todo.data.dao.TaskDAO

class TaskSwipeController(private val taskDAO: TaskDAO, private val taskAdapter: TaskAdapter): ItemTouchHelper.Callback()
{
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int
    {
        return makeMovementFlags(0, ItemTouchHelper.END)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
    {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
    {
        val taskDelete = taskDAO.getByPosition(viewHolder.adapterPosition) ?: return
        taskDAO.delete(task = taskDelete)
        taskAdapter.notifyDataSetChanged()
    }
}