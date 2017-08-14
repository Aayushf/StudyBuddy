package objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by aayushf on 14/8/17.
 */
class Topic(var name:String, var subjectid:Long):RealmObject() {
    @PrimaryKey
    var id:Long = 0
    init {
        id = Calendar.getInstance().timeInMillis
    }
}