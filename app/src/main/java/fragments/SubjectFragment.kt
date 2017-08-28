package fragments


import ViewItems.TopicViewItem
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.listeners.ClickEventHook
import databaseandstorage.RealmInteractor
import kotlinx.android.synthetic.main.fragment_subject.*
import studybuddy.aayushf.studybuddy.MainActivity

import studybuddy.aayushf.studybuddy.R


/**
 * A simple [Fragment] subclass.
 */
class SubjectFragment(var subjectID:Long = 0) : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_subject, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshView()
    }

    fun refreshView(){
        val fadap:FastItemAdapter<TopicViewItem> = FastItemAdapter()
        fadap.withEventHook(object : ClickEventHook<TopicViewItem>() {
            override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                return (viewHolder as TopicViewItem.ViewHolder).fabtopic

            }
            override fun onClick(v: View?, position: Int, fastAdapter: FastAdapter<TopicViewItem>, item: TopicViewItem) {
                (activity as MainActivity).goToRevise(item.topicID)
            }


        })
        val listOfTopics = RealmInteractor.getAllTopicIDsOfSubject(activity, subjectID)
        listOfTopics.forEach {t ->
            fadap.add(TopicViewItem(t, activity))
        }
        rvsubjectfrag.layoutManager  = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvsubjectfrag.adapter = fadap


    }

}// Required empty public constructor
