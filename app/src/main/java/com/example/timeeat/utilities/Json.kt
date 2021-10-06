package com.example.timeeat.utilities

import android.util.JsonReader
import android.util.JsonWriter
import com.example.timeeat.data.Product
import java.io.*


fun writeProduct(writer: JsonWriter, product: Product){
    writer.beginObject()
    writer.name("name").value(product.name)
    writer.endObject()
}
fun writeProductArray(writer: JsonWriter, products:List<Product>){
    writer.beginArray()
    for(product in products)
    {
        writeProduct(writer, product)
    }
    writer.endArray()
}
//写
fun writeJsonStream(out: OutputStream, products:List<Product>){
    val writer= JsonWriter(OutputStreamWriter(out,"utf-8"))//这东西是啥？
    writer.setIndent("    ")//设置缩进格式
    writeProductArray(writer,products)
    writer.close()
}
//读
fun readJsonStream(inputStream: InputStream):List<Product>{
    val reader= JsonReader(InputStreamReader(inputStream,"UTF-8"))
    try {
        return readProductArray(reader)
    }finally {
        reader.close()
    }
}
//读取json数组中的每一元素，就是Product对象
fun readProductArray(reader: JsonReader):List<Product>
{
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
    var name =""
    reader.beginObject()
    while(reader.hasNext()) {
        val field = reader.nextName()

       /* if (field.equals("id")) {
            id = reader.nextString()
        }
        else*/ if (field.equals("name")){
            name=reader.nextString()
        }
        else{
            reader.skipValue()
        }
    }
    reader.endObject()
    return Product(name)
}