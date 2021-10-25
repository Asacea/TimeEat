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
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class RecordActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        fun getJsonString():String?{
            val jsonFilePath=getExternalFilesDir(null)?.absolutePath+"time_data.json" //获取到json文件的地址val
            var json:String?=null
            val charset:Charset=Charsets.UTF_8
            try{
                val jsonFile=FileInputStream(jsonFilePath)
                val size=jsonFile.available()
                val buffer=ByteArray(size)
                jsonFile.read(buffer)
                jsonFile.close()//用完流要记得关闭
                json=String(buffer,charset)
            }catch(e:IOException){
                e.printStackTrace()
                return null
            }
            return json
        }//得到json格式的字符串


        val jsonFile = getExternalFilesDir(null)?.absolutePath + "time_data.json"


            val timeRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_1)
            timeRecyclerView.layoutManager = LinearLayoutManager(this)


             val fis=FileInputStream(jsonFile)
        //注意这里有较大改动
            //val timelist = Gson().fromJson(getJsonString(), arrayListOf<Product>().javaClass)//直接得到arraylist<product>,不用多读啦
            val timelist = readJsonStream(fis)
            timeRecyclerView.adapter = MyAdapter(timelist)


            button_1.setOnClickListener {
                val intent = Intent(this, FirstActivity::class.java)
                startActivity(intent)
            }
    }
}





