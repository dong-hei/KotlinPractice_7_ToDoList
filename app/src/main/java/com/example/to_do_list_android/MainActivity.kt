package com.example.to_do_list_android

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_list_android.databinding.ActivityMainBinding
import com.example.to_do_list_android.db.AppDatabase
import com.example.to_do_list_android.db.ToDoDao
import com.example.to_do_list_android.db.ToDoEntity


class MainActivity : AppCompatActivity() , OnItemLongClickListener {

    private lateinit var binding : ActivityMainBinding
// ActivityMainBinding이 Import 안되서 애먹었는데 viewBinding{
//        enabled = true
//    } 을 build.gradle에서 설정 안하면 임포트가 안된다.
//    선언,초기화 필요없이 바로 바인딩 해줄수있음 ex lateinit var btn : Button

    private lateinit var db : AppDatabase
    private lateinit var todoDao : ToDoDao
    private lateinit var todoList : ArrayList<ToDoEntity>
    private lateinit var adapter: ToDoRecyclerViewAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //위에 binding을 선언했기 때문에 on create() {
    // btn = findViewById(R.id.btn_add_todo)
    // btn.setOnClickListener()
    // binding.btnAddTodo.setOnClickListener()를 쓸필요가 없다}

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()
//        db 설정

        getAllTodoList()

        binding.btnAddTodo.setOnClickListener{
            val intent = Intent(this,AddToDoActivity::class.java)
            startActivity(intent)
        }  // addTodo 버튼을 누르면 AddToDoActivity로간다.
    }

    private fun getAllTodoList(){
        Thread {
            todoList = ArrayList(todoDao.getAllTodo())
            setRecyclerView()
//            리사이클러뷰를 설정해서 리스트를 가져온다.
        }.start()
    }

    private fun setRecyclerView(){
        runOnUiThread{
            adapter = ToDoRecyclerViewAdaptor(todoList, this)

            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
//      리사이클러뷰가 안됐었는데 리사이클러뷰가 있는 xml이 <androidx.recyclerview.widget.RecyclerView 로 설정되어있는지 확인해야한다.
//      뷰 리소스에 접근하기 때문에  메인스레드에서 실행
    }

    override fun onRestart(){
        super.onRestart()
        getAllTodoList()
    }

    override fun onLongClick(position: Int) {
        val builder : AlertDialog.Builder  = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_no),null)
        builder.setPositiveButton(getString(R.string.alert_yes),
        object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int){
                deleteToDo(position)
            }
        })
        builder.show()
    }
    private fun deleteToDo(position: Int){
        Thread{
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)
            runOnUiThread{
                adapter.notifyDataSetChanged()
                Toast.makeText(this,"삭제되었습니다", Toast.LENGTH_SHORT).show()
            }
//         notifyDataSetChanged()   데이터 셋이 바뀜을 알려준다
        }.start()
    }
}