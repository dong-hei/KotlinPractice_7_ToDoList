package com.example.to_do_list_android.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoEntity (
    @PrimaryKey(autoGenerate = true) var id : Int? = null, //프라이머리키로 식별이 가능한 아이디 자동 생성
    @ColumnInfo(name="title") var title : String,
    @ColumnInfo(name="importance") var importance : Int
        )