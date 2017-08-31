package fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import databaseandstorage.RealmInteractor
import databaseandstorage.StorageInteractor
import kotlinx.android.synthetic.main.fragment_definition.view.*
import kotlinx.android.synthetic.main.item_fragment_content.view.*
import objects.Constant
import objects.Definition
import studybuddy.aayushf.studybuddy.MainActivity

import studybuddy.aayushf.studybuddy.R


/**
 * A simple [Fragment] subclass.
 */
class ItemFragment(val itemID:Long = 0, val c:Context? = null) : Fragment() {
var item = RealmInteractor.getItem(c!!, itemID)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return when (item) {
            is Definition -> inflater!!.inflate(R.layout.fragment_definition, container, false)
            is Constant -> inflater!!.inflate(R.layout.fragment_definition, container, false)
            else -> null
        }

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        if(item is Definition) {
            val v = view!!
            if ((item as Definition).scribblePath != null) {
                v.item_frag_imgview.setImageBitmap(StorageInteractor.getBitmapFromFile(context, (item as Definition).scribblePath!!))
            }
            v.tvsec_item_frag.text = (item as Definition).definition
            v.tvmain_item_frag.text = (item as Definition).name
            v.fab.setOnClickListener {
                (c as MainActivity).speakItem(itemID)
            }
        }else if(item is Constant ){
            val v = view!!
            if ((item as Constant).scribblePath != null) {
                v.item_frag_imgview.setImageBitmap(StorageInteractor.getBitmapFromFile(context, (item as Constant).scribblePath!!))
            }
            v.tvsec_item_frag.text = (item as Constant).name
            v.tvmain_item_frag.text = (item as Constant).value
            v.fab.setOnClickListener {
                (c as MainActivity).speakItem(itemID)
            }

        }

    }

}
