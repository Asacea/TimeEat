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
        }//很笨的方法得到时间，，，，太他妈笨了吧


        fun getJsonString():String?{
            val jsonFilePath=getExternalFilesDir(null)?.absolutePath+"time_data.json" //获取到json文件的地址val
            var json:String?=null
            val charset:Charset=Charsets.UTF_8
            try{
                val jsonFile=FileInputStream(jsonFilePath)//打开文件
                val size=jsonFile.available()//读出可读字节的大小
                val buffer=ByteArray(size)//字节数组
                jsonFile.read(buffer)//调用InputStream的read方法，从输入流中读取一定数量的字节，并将其储存在缓存区数组buffer中
                jsonFile.close()//用完流要记得关闭
                json=String(buffer,charset)//String函数，将字节数组buffer以指定的编码格式charset进行解码，最终得到的是json字符串
            }catch(e:IOException){
                e.printStackTrace()
                return null
            }
            return json
        }//得到json格式的字符串

        fun write(filePath:String,jsonString:String){//把json 格式的字符串写进json文件
            val file=File(filePath)
            try{
                val outputStreamWriter=OutputStreamWriter(FileOutputStream(file))//打开该文件
                outputStreamWriter.write(jsonString)//调用write方法
                outputStreamWriter.close()//关闭输出流
            }catch(e:Exception){
              e.printStackTrace()
            }
        }


        fun saveJson() {
            //将json格式的字符串改写成arrylist类型的数据，首先要得到json格式的字符串
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
            Thread(Runnable {//通过对象表达式来创建一个线程，实际则为实现了ruunable 接口，或者理解为thread的ruunable匿名实例的run方法的重写
                try{
                    var flag=true
                    while(flag){
                        Thread.sleep(1000)//延迟
                        runOnUiThread {//回到主线程，ui线程 ，，还可以用handle的方法
                            text_view_1.text=timecheck()
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


        //点击事件监听器
        button_1.setOnClickListener {
            text_view_1.text = timecheck()
        }

        button_2.setOnClickListener {
            val jsonFilePath=getExternalFilesDir(null)?.absolutePath+"time_data.json"
            //val file=File(jsonFilePath)
            val fis=FileInputStream(jsonFilePath)//fileInPutStream是InputStream的子类
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

