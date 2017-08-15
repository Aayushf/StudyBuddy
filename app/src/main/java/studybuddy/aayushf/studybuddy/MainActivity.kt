package studybuddy.aayushf.studybuddy

import adapters.MainTabsPager
import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import org.jetbrains.anko.customView
import database.RealmInteractor
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.editText

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mMainTabsPager: MainTabsPager? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mMainTabsPager = MainTabsPager(supportFragmentManager, this)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById<View>(R.id.container) as ViewPager
//        mViewPager!!.adapter = mMainTabsPager

//        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
//        tabLayout.setupWithViewPager(mViewPager)

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            addSubject()



        }
        updateTabs()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    fun addSubject(){
        alert {
            customView {
                val e = editText {
                    hint = "Enter Subject Name Here"
                }
                positiveButton("Add This Subject", {
                    RealmInteractor.addSubjectToDatabase(this@MainActivity, e.getText().toString())
                    updateTabs()

                })

            }

        }.show()

    }
    fun updateTabs(){
        if (RealmInteractor.getAllSubjects(this).size!=0) {
            mViewPager?.adapter = MainTabsPager(supportFragmentManager, this)
            (tabs as TabLayout).setupWithViewPager(mViewPager)
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    
}
