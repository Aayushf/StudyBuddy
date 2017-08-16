package database

import android.content.Context
import io.realm.Realm
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



    }
}