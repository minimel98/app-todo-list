package fr.esimed.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Category(@PrimaryKey(autoGenerate = true) var id:Long? = null, var nameCategory:String): Serializable
{
    override fun toString(): String
    {
        return String.format("%s", nameCategory)
    }

    override fun equals(other: Any?): Boolean
    {
        if(this === other)
        {
            return true
        }

        if (javaClass != other?.javaClass)
        {
            return false
        }

        other as Category

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