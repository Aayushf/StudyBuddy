package objects

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by aayushf on 30/8/17.
 */
open class Point(var topicID:Long = 0, var point:String = "Unspecified", var scribblePath:String? = null ) : RealmObject() {
    @PrimaryKey
    var itemid:Long = 0
    init {
        itemid = Calendar.getInstance().timeInMillis
    }
    companion object {
        val TYPE = 2
    }


}
