package databaseandstorage

import android.content.Context
import io.realm.Realm
import io.realm.RealmObject
import objects.Constant
import objects.Definition
import objects.Subject
import objects.Topic

/**
 * Created by aayushf on 14/8/17.
 */
class RealmInteractor {
    companion object {
        fun addSubjectToDatabase(c:Context, name:String){
            Realm.init(c)
            var r = Realm.getDefaultInstance()
            r.beginTransaction()
            r.copyToRealm(Subject(name))
            r.commitTransaction()
        }


        fun getAllSubjects(c:Context):List<Subject>{
            Realm.init(c)
            val results = Realm.getDefaultInstance().where(Subject::class.java).findAll()

            return results.toList()

        }


        fun getSubjectOfID(c:Context, id:Long):String{
            Realm.init(c)
            var s = Realm.getDefaultInstance().where(Subject::class.java).equalTo("id", id).findFirst()
            return s.name


        }
        fun getAllSubjectStrings(c:Context):List<String>{
            return getAllSubjects(c).map { it.name }


        }
        fun getIdOfSubject(c:Context, name:String):Long{
            Realm.init(c)
            return Realm.getDefaultInstance().where(Subject::class.java).equalTo("name", name).findFirst().id

        }
        fun addTopicToDatabase(c:Context, t: Topic){
            Realm.init(c)
            var r = Realm.getDefaultInstance()
            r.beginTransaction()
            r.copyToRealm(t)
            r.commitTransaction()

        }
        fun getTopicNameFromID(c:Context, id:Long):String{
            Realm.init(c)
             return Realm.getDefaultInstance().where(Topic::class.java).equalTo("id", id).findFirst().name
        }
        fun getAllTopicIDsOfSubject(c:Context, subjectID:Long):List<Long>{
            Realm.init(c)
            val l = Realm.getDefaultInstance().where(Topic::class.java).equalTo("subjectid", subjectID).findAll().toList()
            return l.map {
                it.id
            }

        }
        fun getAllTopicStringsOfSubject(c:Context, subjectID:Long):List<String>{
            Realm.init(c)
            val l = Realm.getDefaultInstance().where(Topic::class.java).equalTo("subjectid", subjectID).findAll().toList()
            return l.map {
                it.name
            }

        }
        fun getTopicIdFromString(c:Context,topic:String):Long{
            Realm.init(c)
            return Realm.getDefaultInstance().where(Topic::class.java).equalTo("name", topic).findFirst().id
        }

        fun addItemToDatabase(c: Context, i: Any) {
            Realm.init(c)
            val r = Realm.getDefaultInstance()
            r.beginTransaction()
            r.copyToRealm(i as RealmObject)
            r.commitTransaction()
        }
        fun getItem(c:Context, id:Long):Any{
            Realm.init(c)
            val constant = Realm.getDefaultInstance().where(Constant::class.java).equalTo("itemid", id).findFirst()
            return Realm.getDefaultInstance().where(Definition::class.java).equalTo("itemid", id).findFirst() ?: constant
        }
        fun getItemIDsOfTopic(c:Context, topicid:Long):List<Long>{
            Realm.init(c)
            val definitions = Realm.getDefaultInstance().where(Definition::class.java).equalTo("topicID", topicid).findAll().toList()
            val constants = Realm.getDefaultInstance().where(Constant::class.java).equalTo("topicID", topicid).findAll().toList()
            val listofall: MutableList<Any> = mutableListOf()
            listofall.addAll(definitions)
            listofall.addAll(constants)

            return listofall.map {
                var itemid: Long = 0
                if (it is Definition) {
                    itemid = it.itemid
                } else if (it is Constant) {
                    itemid = it.itemid
                }
                return@map itemid
            }
        }





    }
}