package studybuddy.aayushf.studybuddy

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drawing.*
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import databaseandstorage.StorageInteractor
import customviews.DrawView
import org.jetbrains.anko.*
import org.polaric.colorful.ColorPickerDialog


class DrawingActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        drawview.isDrawingCacheEnabled = true
        fabsavedraw.setOnClickListener {
            obtainStoragePermission()
            val path = StorageInteractor.storeImageToStorage(this, drawview.drawingCache)
            toast(path)
            val i = Intent()
            i.putExtra("drawingPath", path)
            setResult(0, i)
            drawview.destroyDrawingCache()
            finish()
            /*val imgSaved = MediaStore.Images.Media.insertImage(
                    contentResolver, drawview.getDrawingCache(),
                    UUID.randomUUID().toString() + ".png", "drawing")
            if (imgSaved != null){
                toast("Image Saved To:$imgSaved")
            }else{
                toast("Image Not Saved")
            }
            drawview.destroyDrawingCache()
*/
        }
    }
    fun obtainStoragePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }else{

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_drawing, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when(id){
            R.id.action_pen -> showPrefsDialog()
            R.id.action_erase -> drawview.setEraseMode()
            R.id.action_write -> drawview.unSetEraseMode()

        }
        return true
    }
    fun showPrefsDialog(){
        alert {
            customView {
                button {
                    text = "Choose Pen Colour"
                    setOnClickListener {
                        val cpd = ColorPickerDialog(this@DrawingActivity)
                                cpd.setOnColorSelectedListener ({ c->
                            (drawview as DrawView).paintColour = resources.getColor(c.colorRes)
                        })
                        cpd.show()
                    }
                }
            }
        }.show()
    }

}
