package objects

import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.RealmClass

/**
 * Created by aayushf on 17/8/17.
 */
@RealmClass
class Definition(topicID:Long, var name:String = "Unspecified", var definition:String = "Unspecified") : DataItem(topicID), RealmModel {

}