package studybuddy.aayushf.studybuddy

import android.app.Application
import org.polaric.colorful.Colorful

/**
 * Created by aayushf on 19/8/17.
 */
class StudyBuddyAppClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Colorful.init(this)

    }
}