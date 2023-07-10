package com.example.to_do_list_android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ToDoEntity::class),version = 1) // DB가 되는 조건 1 : 어떤 Entity를 넣을것인지 어레이로 명시한다
abstract class AppDatabase : RoomDatabase() { //DB가 되는 조건 2: Room DB를 상속받아야됨

    abstract fun getTodoDao() : ToDoDao //DB가 되는 조건 3: 추상화 함수가 있어야함

    companion object { //싱글톤 패턴
        val databaseName = "db_todo" //DB 이름이다
        var appDatabase : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase? {
            if(appDatabase == null){
                appDatabase =  Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    databaseName).
                fallbackToDestructiveMigration()
                    .build()
            }
            return  appDatabase
        }
    }

}
