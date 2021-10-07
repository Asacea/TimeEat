package com.example.timeeat

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.timeeat.data.Product
import com.example.timeeat.utilities.MyAdapter
import com.example.timeeat.utilities.readJsonStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class RecordActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val jsonFile=getExternalFilesDir(null)?.absolutePath+"time_data.json"



        val timeRecyclerView= findViewById<RecyclerView>(R.id.recyclerview_1)
        timeRecyclerView.layoutManager=LinearLayoutManager(this)


        val fis=FileInputStream(jsonFile)
        val list= readJsonStream(fis)
        timeRecyclerView.adapter= MyAdapter(list)


        button_1.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }
    }
}




