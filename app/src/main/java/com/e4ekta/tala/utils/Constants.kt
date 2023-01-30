package com.e4ekta.tala.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object Constants {
    /* I have published API ON railway app free server, getting requested API from there,*/
    // https://tala-mock-server-production.up.railway.app/records?pagesize=3&pageno=3

    const val BASE_URL = "https://tala-mock-server-production.up.railway.app"

    /* Assumptions : Locals.json in local json file,so I have read it an parse in into map
    as it having dynamic keys like ke ms kh */
    fun loadJSONFromAsset(context: Context, fileName :String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = context.assets.open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}