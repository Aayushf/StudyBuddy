package fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import databaseandstorage.RealmInteractor

import studybuddy.aayushf.studybuddy.R


/**
 * A simple [Fragment] subclass.
 */
class ItemFragment(itemID:Long = 0, c:Context? = null) : Fragment() {
var item = RealmInteractor.getItem(c!!, itemID)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.item_fragment, container, false)
    }

}// Required empty public constructor
