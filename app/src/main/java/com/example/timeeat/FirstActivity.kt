package com.example.timeeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.first_layout.*
import android.content.Intent
import com.example.timeeat.data.Product
import com.google.gson.Gson
import java.io.*
import java.lang.Exception
import java.nio.charset.Charset

class FirstActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)



        fun timecheck():String {
            var time:Long=System.currentTimeMillis()
            var hour:Long
            val minute:Long
            val second:Long
            time/=1000//秒数
            second=time%60//单独的秒数
            time-=second
            time/=60//总分钟数
            minute=time%60//多出的分钟（不足一小时
            time-=minute
            time/=60//总小时数
            hour=time%24+8
            if(hour>=24){
                hour-=24}
            val str="$hour:$minute:$second"
            return str
        }//很笨的方法得到时间


        fun getJsonString():String?{
            val jsonFilePath=getExternalFilesDir(null)?.absolutePath+"time_data.json" //获取到json文件的地址val
            var json:String?=null
            val charset:Charset=Charsets.UTF_8
            try{
                val jsonFile=FileInputStream(jsonFilePath)
                //val jsonFile=assets.open("time_data.json")
                val size=jsonFile.available()
                val buffer=ByteArray(size)
                jsonFile.read(buffer)
                jsonFile.close()
                json=String(buffer,charset)
            }catch(e:IOException){
                e.printStackTrace()
                return null
            }
            return json
        }//得到json格式的字符串

        fun write(filePath:String,jsonString:String){
            val file=File(filePath)
            try{
                val outputStreamWriter=OutputStreamWriter(FileOutputStream(file))
                outputStreamWriter.write(jsonString)
                outputStreamWriter.close()
            }catch(e:Exception){
              e.printStackTrace()
            }
        }


        fun saveJson() {
            val timelist = Gson().fromJson(getJsonString(), arrayListOf<Product>().javaClass)
            timelist.add(Product(timecheck()))
            //在重新转化为json格式字符串
            val jsonString = Gson().toJson(timelist)
            val jsonFilePath = getExternalFilesDir(null)?.absolutePath + "time_data.json"
            //怎么把jsonstring写进文件？？？
            write(jsonFilePath, jsonString)

            //File(jsonFilePath).writeText(jsonString,charset)
        }


        fun initThread(){
            Thread(Runnable {
                try{
                    var flag=true
                    while(flag){
                        Thread.sleep(1000)
                        runOnUiThread { text_view_1.text=timecheck()
                            button_4.setOnClickListener {
                                flag=false
                            }
                        }
                    }
                }catch(e:InterruptedException){
                    e.printStackTrace()
                }
            }).start()
        }


        button_1.setOnClickListener {
            text_view_1.text = timecheck()
        }

        button_2.setOnClickListener {
            val jsonFilePath=getExternalFilesDir(null)?.absolutePath+"time_data.json"
            //val file=File(jsonFilePath)
            val fis=FileInputStream(jsonFilePath)
            if(fis.available()!=0) {
                saveJson()
            }else{
                val timelist= arrayListOf<Product>()
                timelist.add(Product(timecheck()))
                val jsonString=Gson().toJson(timelist)
                write(jsonFilePath,jsonString)
            }

            val intent=Intent(this,RecordActivity::class.java)
            startActivity(intent)

        }


        button_3.setOnClickListener{
            initThread()
        }

}

}

