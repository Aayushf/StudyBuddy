package fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import databaseandstorage.RealmInteractor
import databaseandstorage.StorageInteractor
import kotlinx.android.synthetic.main.item_fragment.view.*
import kotlinx.android.synthetic.main.item_fragment_content.view.*
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
        return inflater!!.inflate(R.layout.item_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val v = view!!
        if((item as Definition).scribblePath != null){
            v.item_frag_imgview.setImageBitmap(StorageInteractor.getBitmapFromFile(context, (item as Definition).scribblePath!! ))
        }
        v.tvsec_item_frag.text = (item as Definition).definition
        v.tvmain_item_frag.text = (item as Definition).name
        v.fab.setOnClickListener {
            (c as MainActivity).speakItem(itemID)
        }

    }

}
