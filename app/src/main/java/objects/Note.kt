package objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by aayushf on 14/8/17.
 */
class Note(var note:String = "Unspecified", var question:String? = null, var subject:Subject? = null, var topic: Topic? = null ):RealmObject() {
@PrimaryKey
    var id:Long = 0
    init {
        id = Calendar.getInstance().timeInMillis
    }
}