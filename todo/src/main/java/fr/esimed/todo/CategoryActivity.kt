package fr.esimed.todo

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.todo.data.dao.TodoDatabase
import fr.esimed.todo.data.model.Category

class CategoryActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(findViewById(R.id.toolbar))

        val db = TodoDatabase.getDatabase(this)
        db.seed()

        val categoryDAO = db.categoryDAO()
        val lstCategory = findViewById<RecyclerView>(R.id.listCat)

        lstCategory.layoutManager = LinearLayoutManager(this)
        lstCategory.adapter = CategoryAdapter(this, categoryDAO)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val dlg = Dialog(this)
            dlg.setContentView(R.layout.category_add)
            dlg.findViewById<Button>(R.id.dialog_button_cancel).setOnClickListener {
                dlg.dismiss()
            }
            dlg.findViewById<Button>(R.id.dialog_button_add).setOnClickListener {
                val editNameCat = dlg.findViewById<EditText>(R.id.dialog_editText_nameCategory)
                val nameCat = editNameCat.text.toString()

                if (nameCat.isEmpty() || nameCat.isBlank())
                {
                    Toast.makeText(this, "le champ est vide", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val cat = Category(nameCategory = nameCat)
                categoryDAO.insert(cat)

                (lstCategory.adapter as CategoryAdapter).notifyDataSetChanged()
                dlg.dismiss()
            }
            dlg.show()
        }
    }

    override fun onResume()
    {
        super.onResume()
        val lstCategory = findViewById<RecyclerView>(R.id.listCat)
        (lstCategory.adapter as CategoryAdapter).notifyDataSetChanged()
    }
}