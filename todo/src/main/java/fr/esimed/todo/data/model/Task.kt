package fr.esimed.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(foreignKeys = [ ForeignKey(entity = Category::class,
                            parentColumns = ["id"],
                            childColumns = ["category"],
                            onDelete = ForeignKey.CASCADE)
                        ])

data class Task(@PrimaryKey(autoGenerate = true) var id:Long? = null, var task:String, var done:Boolean = false, var category:Long)
{

    override fun toString(): String
    {
        return String.format("%s", task)
    }

    override fun equals(other: Any?): Boolean
    {
        if (this === other)
        {
            return true
        }

        if (javaClass != other?.javaClass)
        {
            return false
        }

        other as Task

        if (id != other.id)
        {
            return false
        }

        return true
    }

    override fun hashCode(): Int
    {
        return id.hashCode()
    }

}