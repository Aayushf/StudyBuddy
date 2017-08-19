package objects

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by aayushf on 17/8/17.
 */
open class Definition(var topicID:Long = 0, var name:String = "Unspecified", var definition:String = "Unspecified") : RealmObject() {
    @PrimaryKey
    var itemid:Long = 0
    init {
        itemid = Calendar.getInstance().timeInMillis
    }
    companion object {
        val TYPE = 0
    }


}