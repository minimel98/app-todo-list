package fr.esimed.todo.data.dao

import androidx.room.*
import fr.esimed.todo.data.model.Category
import fr.esimed.todo.data.model.Task

@Dao
interface CategoryDAO
{
    @Query("SELECT * FROM category ORDER BY id, nameCategory")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category ORDER BY id, nameCategory LIMIT 1 OFFSET :position")
    fun getByPosition(position: Int): Category?

    @Query("SELECT COUNT(*) FROM category")
    fun count(): Int

    @Insert
    fun insert(category: Category): Long

    @Update
    fun update(category: Category)

    @Delete
    fun delete(category: Category)

}