package fr.esimed.todo.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.esimed.todo.data.model.Category
import fr.esimed.todo.data.model.Task
import java.text.ParseException

@Database(entities = [Task::class, Category::class], version = 1)

abstract class TodoDatabase: RoomDatabase()
{
    abstract fun taskDAO(): TaskDAO
    abstract fun categoryDAO(): CategoryDAO

    fun seed()
    {
        try
        {
            if (categoryDAO().count() == 0)
            {
                val category1 = Category(nameCategory = "cours")
                val id1 = categoryDAO().insert(category1)

                val category2 = Category(nameCategory = "courses")
                val id2 = categoryDAO().insert(category2)

                if (taskDAO().count() == 0)
                {
                    val task1 = Task(task = "Finir la page 6", done = true, category = id1)
                    val task2 = Task(task = "acheter des pâtes", done = false, category = id2)

                    taskDAO().insert(task1)
                    taskDAO().insert(task2)
                }

            }

        }
        catch (pe: ParseException)
        {
        }
    }

    companion object
    {
       var INSTANCE: TodoDatabase? = null

        fun getDatabase(context:Context): TodoDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db").allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }
    }
}