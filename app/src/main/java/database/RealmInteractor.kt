package database

import android.content.Context
import io.realm.Realm
import objects.Subject

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



    }
}