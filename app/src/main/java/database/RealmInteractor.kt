package database

import android.content.Context
import io.realm.Realm
import objects.Note
import objects.Subject
import objects.Topic

/**
 * Created by aayushf on 14/8/17.
 */
class RealmInteractor {
    companion object {
        fun addSubjectToDatabase(c:Context, name:String){
            Realm.init(c)
            Realm.getDefaultInstance().copyToRealm(Subject(name))
        }
        fun addTopicToDatabase(c:Context, nameOfTopic:String, subjectId:Long){
            Realm.init(c)
            Realm.getDefaultInstance().copyToRealm(Topic(nameOfTopic, subjectId))
        }
        fun addNoteToDatabase(c:Context, note:Note){
            Realm.init(c)
            Realm.getDefaultInstance().copyToRealm(note)
        }
        fun getAllSubjects(c:Context):List<Subject>{
            Realm.init(c)
            val results = Realm.getDefaultInstance().where(Subject::class.java).findAll()
            return results.toList()

        }
        fun getAllTopics(c:Context):List<Topic>{
            Realm.init(c)
            val results = Realm.getDefaultInstance().where(Topic::class.java).findAll()
            return results.toList()

        }
        fun getTopicsOfSubject(c:Context, subjectId: Long):List<Topic>{
            Realm.init(c)
            val results = Realm.getDefaultInstance().where(Topic::class.java).equalTo("subjectId", subjectId).findAll()
            return results.toList()

        }



    }
}