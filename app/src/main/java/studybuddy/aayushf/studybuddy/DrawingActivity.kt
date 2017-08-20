package studybuddy.aayushf.studybuddy

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drawing.*
import android.Manifest.permission
import android.Manifest.permission.WRITE_CALENDAR
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.UUID.randomUUID
import android.provider.MediaStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.nio.file.Files.exists
import android.media.MediaScannerConnection
import android.net.Uri


class DrawingActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        drawview.isDrawingCacheEnabled = true
        fabsavedraw.setOnClickListener {
            obtainStoragePermission()
            saveImage()
            val imgSaved = MediaStore.Images.Media.insertImage(
                    contentResolver, drawview.getDrawingCache(),
                    UUID.randomUUID().toString() + ".png", "drawing")
            if (imgSaved != null){
                toast("Image Saved To:$imgSaved")
            }else{
                toast("Image Not Saved")
            }
            drawview.destroyDrawingCache()

        }
    }
    fun obtainStoragePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }else{

        }

    }
    fun saveImage(){
        val f = File(Environment.getExternalStorageDirectory().toString())
        var success = false
            if (!f.exists()){
                success = f.mkdirs()
        }
        info("$success$f")
        val file = File(Environment.getExternalStorageDirectory().toString() + "/${UUID.randomUUID()}.png")
        if (!file.exists()) {
            try {
                success = file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            info("$success$file")
            var ostream: FileOutputStream? = null
                ostream = FileOutputStream(file)
                val drawing = drawview.drawingCache
                val save = drawing.compress(Bitmap.CompressFormat.PNG, 100, ostream)
            MediaScannerConnection.scanFile(this,
                    arrayOf(file.toString()), null
            ) { path, uri ->
                info("Scanned $path:")
                info("-> uri=" + uri)
            }


        }

    }

}
