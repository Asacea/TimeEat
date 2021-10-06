package com.example.timeeat

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.first_layout.*
import java.text.SimpleDateFormat
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import android.util.JsonReader
import android.util.JsonWriter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.timeeat.data.Product
import com.example.timeeat.utilities.readJsonStream
import com.example.timeeat.utilities.writeJsonStream
import java.io.*

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
        }

        val jsonFile=getExternalFilesDir(null)?.absolutePath+"time_data.json"

        fun saveJson(){

            val fos=FileOutputStream(jsonFile)
             val products = arrayListOf<Product>()
            ////val fis=FileInputStream(jsonFile)
            //val products= readJsonStream(fis)//获取到的是list
            //var productss=products.toMutableList()
            //现在的问题是，每次记录数据就会覆盖上一次的数据：每次的重新定义的product都是空的，想个办法获取到上一次记录下的数据，并且与新的数据组成list
            products.add(Product(timecheck()))
            writeJsonStream(fos,products)
            //闪退了，为什么？？？？

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




           //数据库文件的存储路劲
          //*val filename = filesDir.toString() + "/test.db"
           //val buttonCreatDatabaseTable=findViewById<Button>(R.id.button_2)
           //val textviewQueryResult=findViewById<TextView>(R.id.text_view_1)
           //创建t_test的sql语句
        /*val createTableSQL = """CREATE TABLE[t_test](
         CONSTRAINT [sqlite_autoindex_t_test_1]
         PRIMARY KEY ([id]))"""//id:Integer类型，自增字段
        val file = File(filename)
        if(file.exists())
         {
            file.delete()
        }
           //打开数据库
        val database = SQLiteDatabase.openOrCreateDatabase(filename, null)
           //执行建立t_test表的SQL语句
        database.execSQL(createTableSQL)
         [id] INTERGER,[time]TEXT,
           //关闭数据库
           //database.close()*/
        //我找不到这个数据库在哪里啊啊啊啊啊啊啊啊啊啊哭了
        //我知道了，我用的真机模拟的，好像还要破解什么root权限啥的，爷累了


        //看到了一个json文件储存把数据储存到本地的方法，决定try try
        //val time=TimeRecord(timecheck())
        //Log.i("timeLogging",time.toString())




        button_1.setOnClickListener {
            text_view_1.text = timecheck()
        }

        button_2.setOnClickListener {

            //sharedPreferences好像一个建制只能储存一个数据？
            //var sharedPreferences=getSharedPreferences("test",Activity.MODE_PRIVATE)
            //var editor=sharedPreferences.edit()
            //editor.putString("time",timecheck())
            //editor.apply()

            //向数据表中插入数据
           /* val insertSQL="insert into t_test(time) values(?)"
            database.execSQL(insertSQL, arrayOf(timecheck()))
            //database.close()*/

            //跳转到recordacivity

            //将json 文件保存到sd卡，申请写sd卡权限//现在真的还有人用sd卡吗？

           /* if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                }
            else
            {
                saveJson()
            }*/



            saveJson()

            val intent=Intent(this,RecordActivity::class.java)
           /* val data:String=timecheck()
            intent.putExtra("time",data)*/
            startActivity(intent)

        }

        button_3.setOnClickListener{
            initThread()
        }


    }

}

