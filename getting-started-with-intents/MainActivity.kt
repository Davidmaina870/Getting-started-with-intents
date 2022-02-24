package com.example.intent

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.example.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE: Int = 0
    private lateinit var imageView: ImageView
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnGallery.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }

        binding!!.btnCamera.setOnClickListener {
            val snapPhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (snapPhotoIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(snapPhotoIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }


        binding!!.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This gonna be my text")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share with?")
            startActivity(shareIntent)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 0) {
            val bitmap = data?.extras?.get("data") as Bitmap
            val uri = data.data
            imageView.setImageURI(uri)
            imageView.setImageBitmap(bitmap)
        }
    }
}