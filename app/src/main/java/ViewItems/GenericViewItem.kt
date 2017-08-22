package ViewItems

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import databaseandstorage.RealmInteractor
import objects.Definition
import org.jetbrains.anko.find
import studybuddy.aayushf.studybuddy.R

/**
 * Created by aayushf on 19/8/17.
 */
class GenericViewItem(val c:Context, val itemid:Long) : AbstractItem<GenericViewItem, GenericViewItem.ViewHolder>(){
    val dataItem:Any = RealmInteractor.getItem(c, itemid)
    override fun getViewHolder(v: View?): ViewHolder {
        return ViewHolder(v!!)
    }

    override fun getType(): Int {
        return if (dataItem is Definition){
            Definition.TYPE
        }else{
            -1
        }

    }

    override fun getLayoutRes(): Int {
        val type = type
        return if (type == Definition.TYPE ){
            R.layout.definition_layout
        }else{
            R.layout.error_layout
        }

    }

    override fun bindView(holder: ViewHolder, payloads: MutableList<Any>?) {
        super.bindView(holder, payloads)
        if(dataItem is Definition){
            holder.primaryText?.text = dataItem.name
            holder.secondaryText?.text = dataItem.definition

        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var primaryText:TextView? = null
        var secondaryText:TextView? = null
        var fab:FloatingActionButton? = null
        init {
            if (itemView.id != R.layout.error_layout){
                primaryText = itemView.find(R.id.primarytextitem)
                secondaryText = itemView.find(R.id.secondarytextitem)
                fab = itemView.find(R.id.fabitem)
            }
        }


    }
    companion object {
        fun getListOfItems(c:Context, list:List<Long>):List<GenericViewItem>{
            return list.map { GenericViewItem(c, it) }
        }
        fun getAdapterForTopic(c:Context, topicID:Long):FastItemAdapter<GenericViewItem>{
            val fadap = FastItemAdapter<GenericViewItem>()
            fadap.add(GenericViewItem.getListOfItems(c, RealmInteractor.getItemIDsOfTopic(c, topicID)))
            return fadap

        }
    }
}