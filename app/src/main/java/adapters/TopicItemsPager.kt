package adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.content.Context
import databaseandstorage.RealmInteractor
import fragments.ItemFragment


/**
 * Created by aayushf on 24/8/17.
 */
class TopicItemsPager(fm:FragmentManager, val c:Context, topicID:Long) : FragmentPagerAdapter(fm) {
    val listOfOItems = RealmInteractor.getItemIDsOfTopic(c, topicID)

    override fun getItem(position: Int): Fragment {
        return ItemFragment(listOfOItems[position], c)

    }

    override fun getCount(): Int {
        return listOfOItems.size

    }
}