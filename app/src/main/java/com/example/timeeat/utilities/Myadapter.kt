package com.example.timeeat.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timeeat.R
import com.example.timeeat.data.Product
import kotlinx.android.synthetic.main.item_time.view.*

class MyAdapter(val time:ArrayList<Product>): RecyclerView.Adapter<MyViewHolder>(){

    fun deleteThis(position: Int){
        time.removeAt(position)
        notifyItemRemoved(position)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val timelist=time[position]
        //holder.time.text=timelist.products//绑定模型与视图
        holder.itemView.apply {
            text_view_time.text=timelist.time
        }
        holder.btn.setOnClickListener{
            //点击删除当前项
            deleteThis(position)
        }

    }
    override fun getItemCount(): Int {
        return time.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_time,parent,false)
        val itemViewHolder=MyViewHolder(itemView)

        return MyViewHolder(itemView)
    }
}


class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val time: TextView =itemView.findViewById(R.id.text_view_time)
    val btn:Button=itemView.findViewById(R.id.button_delete)

}

