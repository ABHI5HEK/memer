package com.example.memer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private var imagePath= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load()
    }
    fun load(){
        progressBar.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response -> val ura = response.getString("url")

                // glide image
                Glide.with(this).load(ura).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {

                        progressBar.visibility = View.GONE
                        return false
                    }

                }).into(memeImageView)
                imagePath = ura

                Log.d("load"," working")

            },
            Response.ErrorListener {
                Log.d("load", it.localizedMessage)
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }


    fun next(view: View) {
        load()
    }
    fun share(view: View) {
        val imgIntent = Intent(Intent.ACTION_SEND)
        imgIntent.type= "text/plain"
           imgIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this meme  $imagePath")
           startActivity(Intent.createChooser(imgIntent, "share this meme using..."))
    }

//    fun getBitmapFromView(view: ImageView): Bitmap?{
//        val bitmap = Bitmap.createBitmap(view.width,view.height,Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        view.draw(canvas)
//        return bitmap
//    }
//    fun getUri(inContext: Context, inImage: Bitmap): Uri?{
//        val bytes = ByteArrayOutputStream()
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path = Images.Media.insertImage(inContext,inImage,"title", null)
//        return Uri.parse(path)


 //   }

}