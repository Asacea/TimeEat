package com.example.timeeat.utilities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.timeeat.R
import com.example.timeeat.data.Product
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_time.view.*
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset

class MyAdapter(var time:ArrayList<Product>): RecyclerView.Adapter<MyViewHolder>(){




    fun deleteThisItem(position: Int){
        time.removeAt(position)
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyDataSetChanged()
       // return time
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val timelist=time[position]
        holder.time_text_view.apply {
            text_view_time.text=timelist.time
        }
        holder.btn.setOnClickListener{
            //点击删除当前项
            deleteThisItem(position)
            //val newList=deleteThisItem(position)
            //val newJsonString= Gson().toJson(newList)
        }

    }
    override fun getItemCount(): Int {
        return time.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_time,parent,false)
        val itemViewHolder=MyViewHolder(itemView)

        return itemViewHolder
    }
}


class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val time_text_view: TextView =itemView.findViewById(R.id.text_view_time)
    val btn:Button=itemView.findViewById(R.id.button_delete)

}

