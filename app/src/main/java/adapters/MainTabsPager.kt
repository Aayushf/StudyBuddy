package adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import studybuddy.aayushf.studybuddy.MainActivity

/**
 * Created by aayushf on 14/8/17.
 */
 class MainTabsPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return MainActivity.PlaceholderFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "SECTION 1"
            1 -> return "SECTION 2"
            2 -> return "SECTION 3"
        }
        return null
    }
}