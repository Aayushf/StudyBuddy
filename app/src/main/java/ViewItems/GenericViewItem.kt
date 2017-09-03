package ViewItems

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.listeners.ClickEventHook
import databaseandstorage.RealmInteractor
import objects.Constant
import objects.Definition
import objects.Point
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import studybuddy.aayushf.studybuddy.MainActivity
import studybuddy.aayushf.studybuddy.R

/**
 * Created by aayushf on 19/8/17.
 */
class GenericViewItem(val c:Context, val itemid:Long) : AbstractItem<GenericViewItem, GenericViewItem.ViewHolder>(), AnkoLogger{
    val dataItem:Any = RealmInteractor.getItem(c, itemid)
    override fun getViewHolder(v: View?): ViewHolder {
        return ViewHolder(v!!, type)
    }

    override fun getType(): Int {
        return when (dataItem) {
            is Definition -> Definition.TYPE
            is Constant -> Constant.TYPE
            is Point -> Point.TYPE
            else -> -1
        }

    }

    override fun getLayoutRes(): Int {
        val type = type
        return when (type) {
            Definition.TYPE -> R.layout.definition_layout
            Constant.TYPE -> R.layout.constant_layout
            Point.TYPE -> R.layout.point_layout
            else -> R.layout.error_layout
        }

    }

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        if(dataItem is Definition){
            holder.primaryText?.text = dataItem.name
            holder.secondaryText?.text = dataItem.definition

        } else if (dataItem is Constant) {
            holder.primaryText?.text = dataItem.value
            holder.secondaryText?.text = dataItem.denotion
            holder.tertiaryText?.text = dataItem.name
        } else if (dataItem is Point) {
            holder.primaryText?.text = dataItem.point
        }
    }

    inner class ViewHolder(itemView: View, val type: Int) : RecyclerView.ViewHolder(itemView) {
        var primaryText:TextView? = null
        var secondaryText:TextView? = null
        var tertiaryText: TextView? = null
        var fab:FloatingActionButton? = null

        init {
            if (itemView.id != R.layout.error_layout){
                fab = itemView.find(R.id.fabitem)
                when (type) {
                    Definition.TYPE -> {
                        primaryText = itemView.find(R.id.primarytextitem)
                        secondaryText = itemView.find(R.id.secondarytextitem)
                    }
                    Constant.TYPE -> {
                        tertiaryText = itemView.find(R.id.tertiarytextitem)
                        info("Tertiary Assigned")
                    }
                    Point.TYPE -> primaryText = itemView.find(R.id.primarytextitem)
                }

            }
        }


    }
    companion object {
        fun getListOfItems(c:Context, list:List<Long>):List<GenericViewItem>{
            return list.map { GenericViewItem(c, it) }
        }
        fun getAdapterForTopic(c:Context, topicID:Long):FastItemAdapter<GenericViewItem>{
            val fadap = FastItemAdapter<GenericViewItem>()
            fadap.withEventHook(object: ClickEventHook<GenericViewItem>() {
                override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                        return (viewHolder as GenericViewItem.ViewHolder).fab



                }
                override fun onClick(v: View?, position: Int, fastAdapter: FastAdapter<GenericViewItem>?, item: GenericViewItem) {
                    (item.c as MainActivity).onItemSelected(item.itemid)
                }

            })
            fadap.add(GenericViewItem.getListOfItems(c, RealmInteractor.getItemIDsOfTopic(c, topicID)))

            return fadap

        }
    }
}