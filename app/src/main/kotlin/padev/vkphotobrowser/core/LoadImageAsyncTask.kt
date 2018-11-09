package padev.vkphotobrowser.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


open class LoadImageAsyncTask() : AsyncTask<String, Integer, Bitmap>() {

    var listeners: ArrayList<LoadImageListener> = ArrayList()

    private var id: String? = ""

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): Bitmap? {
        return try {
            val url = URL(params[0])

            if (params.size > 1) {
                id = params[1]
            }

            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)

        for (i in 1..listeners.size) {
            if (id != null) {
                listeners[i-1].imageLoadFinish(id!!, get())
            }
        }
    }
}