package ViewItems

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.mikepenz.fastadapter.items.AbstractItem
import databaseandstorage.RealmInteractor
import org.jetbrains.anko.find
import studybuddy.aayushf.studybuddy.R


/**
 * Created by aayushf on 17/8/17.
 */
class TopicViewItem( val topicID:Long, val c:Context ) : AbstractItem<TopicViewItem, TopicViewItem.ViewHolder>() {
    override fun getViewHolder(v: View?): ViewHolder {
        return ViewHolder(v!!)

    }

    override fun getLayoutRes(): Int {
        return R.layout.topic_card
    }

    override fun getType(): Int {
        return 0

    }

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        holder.tvtopic.text = RealmInteractor.getTopicNameFromID(c,topicID )
        holder.rvtopic.layoutManager = LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false)
        holder.rvtopic.adapter = GenericViewItem.getAdapterForTopic(c, topicID)
        var expanded = false
        holder.expandbutton.setOnClickListener {
            if(!expanded){
                holder.expandbutton.text = "Collapse"
                val lp =  RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.addRule(RelativeLayout.BELOW, R.id.innerrl1)
                holder.llrvtopic.layoutParams = lp
                expanded = true

            }
            else{
                holder.expandbutton.text = "Expand"
                val lp =  RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0)
                lp.addRule(RelativeLayout.BELOW, R.id.innerrl1)
                holder.llrvtopic.layoutParams = lp
                expanded = false

            }
            }



    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvtopic = itemView.find<TextView>(R.id.topic_card_tv)
        val rvtopic = itemView.find<RecyclerView>(R.id.topiccardrv)
        val fabtopic = itemView.find<FloatingActionButton>(R.id.fabcard)
        val llrvtopic = itemView.find<LinearLayout>(R.id.llrvtopic)
        val expandbutton = itemView.find<Button>(R.id.expandbutton)

    }
}