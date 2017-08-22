package databaseandstorage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

/**
 * Created by aayushf on 22/8/17.
 */
class StorageInteractor {
    companion object{
        fun storeImageToStorage(c: Context, d:Bitmap ):String{
            val folder = File(Environment.getExternalStorageDirectory().toString()+"/StudyBuddy/")
            if(!folder.exists()){
                folder.mkdirs()
            }
            val file = File(Environment.getExternalStorageDirectory().toString()+"/StudyBuddy/"+ UUID.randomUUID().toString()+".png")
            if (!file.exists()){
                file.createNewFile()
            }
            val ostream = FileOutputStream(file)
            d.compress(Bitmap.CompressFormat.PNG, 100, ostream)
            MediaScannerConnection.scanFile(c, arrayOf(file.toString()), null){_, _ ->}
            return file.toString()
        }
        fun getBitmapFromFile(c:Context, path:String):Bitmap{
            var f = File(path)
            var instream = FileInputStream(f)
            val opts = BitmapFactory.Options()
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888
            return BitmapFactory.decodeStream(instream, null, opts)
        }
    }
}