package customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.content.res.TypedArray
import android.graphics.Typeface
import studybuddy.aayushf.studybuddy.R


/**
 * Created by aayushf on 31/8/17.
 */
class FontedTextView(val c: Context, val attr: AttributeSet) : TextView(c) {

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }
    fun setup(){
        val am = c.applicationContext.assets

        if (attr != null){
            val a = context.obtainStyledAttributes(attr, R.styleable.FontedTextView)
            val fontName = a.getString(R.styleable.FontedTextView_fontname)

                val tf = Typeface.createFromAsset(am,"fonts/$fontName")
            typeface = tf

        }

    }
}