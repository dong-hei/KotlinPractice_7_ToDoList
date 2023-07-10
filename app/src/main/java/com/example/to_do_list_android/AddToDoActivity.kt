package com.example.to_do_list_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.to_do_list_android.databinding.ActivityAddToDoBinding
import com.example.to_do_list_android.db.AppDatabase
import com.example.to_do_list_android.db.ToDoDao
import com.example.to_do_list_android.db.ToDoEntity

class AddToDoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddToDoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : ToDoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAddToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnComplete.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo(){
        val todoTitle = binding.title.text.toString()
        val todoImportance = binding.radioGroup.checkedRadioButtonId
//        라디오 그룹안에서 체크된 아이디를  가져온다.

        var impData = 0;
        when(todoImportance){
            R.id.btn_high -> {
                impData = 1
            }

            R.id.btn_middle -> {
                impData = 2
            }

            R.id.btn_low -> {
                impData = 3
            }
        }
        if(impData == 0 || todoTitle.isBlank()){
            Toast.makeText(this,"모든 항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread{
                todoDao.insertTodo(ToDoEntity(null,todoTitle,impData))
                runOnUiThread {
                    Toast.makeText(this,"할일이 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()

                }
            }.start()
        }
    }

}