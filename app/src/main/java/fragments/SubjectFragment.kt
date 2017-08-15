package fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import database.RealmInteractor
import kotlinx.android.synthetic.main.fragment_subject.*

import studybuddy.aayushf.studybuddy.R


/**
 * A simple [Fragment] subclass.
 */
class SubjectFragment(var subject:Long = 0) : Fragment() {


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
        subfragtv.text = RealmInteractor.getSubjectOfID(activity, subject)

    }

}// Required empty public constructor
