package fr.esimed.todo.data.dao

import androidx.room.*
import fr.esimed.todo.data.model.Task

@Dao
interface TaskDAO
{
    @Query("UPDATE task SET done = :done WHERE category = :id_category")
    fun checkAll(id_category: Long, done: Boolean)

    @Query("SELECT * FROM task ORDER BY id, task, category")
    fun getAll(): List<Task>

    @Query("SELECT * FROM task ORDER BY id, task, category LIMIT 1 OFFSET :position")
    fun getByPosition(position: Int): Task?

    @Query("SELECT COUNT(*) FROM task WHERE category = :id_category")
    fun getNbTaskByCategory(id_category:Long): Int

    @Query("SELECT * FROM task  WHERE category = :id_category ORDER BY id, task, category LIMIT 1 OFFSET :position")
    fun getTaskByPositionCategory(id_category: Long, position: Int): Task?

    @Query("SELECT COUNT(*) FROM task, category")
    fun count(): Int

    @Insert
    fun insert(task: Task): Long

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)
}