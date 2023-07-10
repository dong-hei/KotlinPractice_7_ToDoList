package com.example.to_do_list_android.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ToDoDao {

    //get All
    @Query("SELECT * FROM TodoEntity")
    fun getAllTodo() : List<ToDoEntity>

    //insert todo
    @Insert
    fun insertTodo(todo: ToDoEntity)

    // delete todo
    @Delete
    fun deleteTodo(todo : ToDoEntity)
}