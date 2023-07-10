package com.example.to_do_list_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list_android.databinding.ItemTodoBinding
import com.example.to_do_list_android.db.ToDoEntity
import java.util.ArrayList

class ToDoRecyclerViewAdaptor (private val todoList: ArrayList<ToDoEntity>,private val listener : OnItemLongClickListener) : RecyclerView.Adapter<ToDoRecyclerViewAdaptor.MyViewHolder>() {
    //뷰 홀더 패턴 : 각 뷰 객체를 각 뷰 홀더에 보관해서 반복 메소드 호출을 줄여 속도를 개선하는 패턴

    inner class MyViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle
//        자동으로 카멜로 변환된다
        
        val root = binding.root
//        루트 레이아웃을 뜻한다
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
// 마이뷰 홀더를 객체로 반환하고 객체를 만드는것

    override fun getItemCount(): Int {
        return todoList.size
    }
// 아이템의 갯수를 반환한다.

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val toDoData = todoList[position]

        when (toDoData.importance){
            1->{
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2->{
                holder.tv_importance.setBackgroundResource(R.color.yellow)
            }
            3->{
                holder.tv_importance.setBackgroundResource(R.color.green)
            }
        }
        holder.tv_importance.text = toDoData.importance.toString()
        holder.tv_title.text = toDoData.title

        holder.root.setOnLongClickListener {
            listener.onLongClick(position)
            false
//            false라고 하면 다른 클릭 이벤트도 실행되고 true라고 하면 오직 onLongClick 만 실행된다.
        }
    }
// 방금 만든 뷰 홀더와 데이터를 묶어준다,뷰홀더 객체에 데이터를 넣어준다
}