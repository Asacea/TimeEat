package com.example.timeeat.utilities

import android.util.JsonReader
import android.util.JsonWriter
import com.example.timeeat.data.Product
import java.io.*
import java.nio.charset.Charset

//读json文件
    fun readJsonStream(inputStream: InputStream):ArrayList<Product>{
       val reader= JsonReader(InputStreamReader(inputStream,"UTF-8"))
       try {
             return readProductArray(reader)
       }finally {
        reader.close()
       }
    }
//读取json数组中的每一元素，就是Product对象
    fun readProductArray(reader: JsonReader):ArrayList<Product> {
         val products=ArrayList<Product>()
         reader.beginArray()
         while(reader.hasNext())
         {
              products.add(readProuct(reader))
         }
         reader.endArray()
         return products
    }

//从json数组中读取product对象
    fun readProuct(reader: JsonReader):Product{
        var time =""
        reader.beginObject()
        while(reader.hasNext()) {
            val field = reader.nextName()
            if (field.equals("time")){
                 time=reader.nextString()
            }
            else{
            reader.skipValue()
            }
        }
        reader.endObject()
        return Product(time)
    }

