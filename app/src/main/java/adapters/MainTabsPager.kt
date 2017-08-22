package adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import databaseandstorage.RealmInteractor
import fragments.SubjectFragment
import objects.Subject

/**
 * Created by aayushf on 14/8/17.
 */
 class MainTabsPager(fm: FragmentManager, c:Context) : FragmentPagerAdapter(fm) {
    var l = listOf<Subject>()
    init {
        l = RealmInteractor.getAllSubjects(c)

    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return SubjectFragment(l[position].id)
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return l.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
    return l[position].name
    }
}