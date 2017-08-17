package objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by aayushf on 16/8/17.
 */
open class Topic(var name :String = "Unspecified",  var subjectid:Long = 0):RealmObject() {
    @PrimaryKey
    var id:Long = 0
    init {
        id = Calendar.getInstance().timeInMillis
    }

}