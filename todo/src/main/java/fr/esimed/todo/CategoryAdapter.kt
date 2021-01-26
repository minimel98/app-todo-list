package fr.esimed.todo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.todo.data.dao.CategoryDAO
import fr.esimed.todo.data.dao.TaskDAO
import fr.esimed.todo.data.dao.TodoDatabase
import fr.esimed.todo.data.model.Category

class CategoryAdapter(private val context:Context, private val categoryDAO: CategoryDAO): RecyclerView.Adapter<CategoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder
    {
        return CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_cell, parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int)
    {
        val taskDAO = TodoDatabase.getDatabase(context).taskDAO()
        val category = categoryDAO.getByPosition(position) ?: return
        holder.titleCategory.text = category.toString()

        val id = category.id ?: return
        val task = taskDAO.getNbTaskByCategory(id_category = id)

        if (task > 1)
        {
            holder.nbTask.text = String.format(context.getString(R.string.plural_task), task)
        }
        else
        {
            holder.nbTask.text = String.format(context.getString(R.string.singular_task), task)
        }

        holder.btnUpdate.setOnClickListener{
            val dlg = Dialog(context)
            dlg.setContentView(R.layout.category_edit)
            dlg.findViewById<EditText>(R.id.dialog_update_nameCategory).setText(category.nameCategory)
            dlg.findViewById<Button>(R.id.dialog_edit_button_cancel).setOnClickListener {
                dlg.dismiss()
            }
            dlg.findViewById<Button>(R.id.dialog_edit_button_update).setOnClickListener {
                val updateNameCat = dlg.findViewById<EditText>(R.id.dialog_update_nameCategory)
                val newNameCat = updateNameCat.text.toString()

                if (newNameCat.isEmpty() || newNameCat.isBlank())
                {
                    Toast.makeText(context, "le champ est vide", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                category.nameCategory = newNameCat
                categoryDAO.update(category)
                notifyDataSetChanged()
                dlg.dismiss()
                Toast.makeText(context,"Modification enregistrée !", Toast.LENGTH_SHORT).show()
            }
            dlg.show()
        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(String.format(context.getString(R.string.confirm_delete_cat), category.toString()))

            builder.setNegativeButton(R.string.dialog_btn_cancel, DialogInterface.OnClickListener{dialog, _ -> dialog.dismiss() })

            builder.setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialog, _ ->
                categoryDAO.delete(category)
                notifyDataSetChanged()
                dialog.dismiss()
            })

            builder.create().show()
        }

        holder.itemView.setOnClickListener {
            val category: Category = categoryDAO.getByPosition(position) ?: return@setOnClickListener
            val intent = Intent(context, TaskActivity::class.java)
            intent.putExtra("category", category)
            context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int
    {
        return categoryDAO.count()
    }
}