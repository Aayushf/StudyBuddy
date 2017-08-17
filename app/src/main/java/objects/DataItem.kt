package objects

import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by aayushf on 17/8/17.
 */
open class DataItem(var topicid:Long = 0 ) {
    @PrimaryKey
    var itemid:Long = 0
    init {
        itemid = Calendar.getInstance().timeInMillis
    }
    
}