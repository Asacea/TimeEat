package com.example.timeeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.first_layout.*
import java.text.SimpleDateFormat

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)

       button_1.setOnClickListener {
           var time: Long = System.currentTimeMillis()
           val hour: Long
           val minute: Long
           val second: Long
           time /= 1000
           second = time % 60
           time -= second
           time /= 60
           minute = time % 60
           time -= minute
           time /= 60
           hour = time % 24 + 8
           val str="$hour:$minute:$second"
           text_view_1.text=str




       }


    }

}
