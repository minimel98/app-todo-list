package fr.esimed.todo

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.todo.data.dao.TodoDatabase
import fr.esimed.todo.data.model.Category
import fr.esimed.todo.data.model.Task

class TaskActivity : AppCompatActivity()
{
    val db = TodoDatabase.getDatabase(this)
    val taskDAO = db.taskDAO()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val category = intent.getSerializableExtra("category") as Category

        val lstTodo = findViewById<RecyclerView>(R.id.todoList)

        lstTodo.layoutManager = LinearLayoutManager(this)
        lstTodo.adapter = TaskAdapter(this, taskDAO, category)

        val taskSwipeController = TaskSwipeController(taskDAO, lstTodo.adapter as TaskAdapter)
        val itemTouchHelper = ItemTouchHelper(taskSwipeController)
        itemTouchHelper.attachToRecyclerView(lstTodo)
        val buttonAdd = findViewById<ImageButton>(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val editTask = findViewById<EditText>(R.id.editTask)
            val task = editTask.text.toString()

            if(task.isEmpty() || task.isBlank())
            {
                Toast.makeText(this, "Attention vous ne pouvez ajouter un champ vide", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val categoryId = category.id ?: return@setOnClickListener
            val taskAdd = Task(task = task, done = false, category = categoryId)
            taskDAO.insert(taskAdd)

            (lstTodo.adapter as TaskAdapter).notifyDataSetChanged()

            editTask.text.clear()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        super.onOptionsItemSelected(item)
        val category = intent.getSerializableExtra("category") as Category
        val idCategory = category.id ?: return true
        val lstTodo = findViewById<RecyclerView>(R.id.todoList)
        lstTodo.layoutManager = LinearLayoutManager(this)
        lstTodo.adapter = TaskAdapter(this, taskDAO, category)

        when(item.itemId)
        {
            R.id.menu_task_checkall -> {
                taskDAO.checkAll(id_category = idCategory, done = true)
                (lstTodo.adapter as TaskAdapter).notifyDataSetChanged()
            }

            R.id.menu_task_uncheckall -> {
                taskDAO.checkAll(id_category = idCategory, done = false)
                (lstTodo.adapter as TaskAdapter).notifyDataSetChanged()
            }
        }
        return true
    }
}