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
import android.widget.Button
import android.widget.EditText

import android.widget.TextView
import database.RealmInteractor
import kotlinx.android.synthetic.main.activity_main.*
import objects.Definition
import objects.Topic
import org.jetbrains.anko.*
import org.jetbrains.anko.selector
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
            itemTypeSelect()

        }
        updateTabs()
        (tabs as TabLayout).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                title = tab!!.text

            }

        })

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


        if (id == R.id.action_add_subject) {
            addSubject()
        }else if(id == R.id.action_add_topic){
            addTopic()
        }

        return super.onOptionsItemSelected(item)
    }

    fun addSubject() {
        alert {
            customView {
                title = "Add A Subject"
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

    fun updateTabs() {
        if (RealmInteractor.getAllSubjects(this).size != 0) {
            mViewPager?.adapter = MainTabsPager(supportFragmentManager, this)
            (tabs as TabLayout).setupWithViewPager(mViewPager)
        }
    }

    fun addTopic() {
        alert {
            val allSubjects = RealmInteractor.getAllSubjectStrings(this@MainActivity)
            var subjectId: Long = 0
            customView {
                verticalLayout {
                    button {
                        text = "Choose Subject"
                        setOnClickListener {
                            selector("Choose Subject", allSubjects, { _, i ->
                                subjectId = RealmInteractor.getIdOfSubject(this@MainActivity, allSubjects[i])
                            })
                        }
                    }
                    title = "Add A Topic"

                    val e = editText {
                        hint = "Enter Topic Name Here"
                    }
                    positiveButton("Add This Topic", {
                        RealmInteractor.addTopicToDatabase(this@MainActivity, Topic(e.text.toString(), subjectId))
                        updateTabs()

                    })

                }

            }


        }.show()

        /**
         * A placeholder fragment containing a simple view.
         */

    }
    fun itemTypeSelect(){
        selector("Select Type Of Item To Add", listOf("Definition"), {_, i->
            if(i==0){
                addDefinition()
            }

        })
    }
    fun addDefinition(){
        alert{
            customView {
                title = "Add A Defenetion"
                var etname:EditText
                var etdefinition:EditText
                var btnTopic:Button
                val allSubjects = RealmInteractor.getAllSubjectStrings(this@MainActivity)
                var subjectId: Long = 0
                var allTopicsOfSubject:List<String> = listOf()
                var topicId: Long = 0
                verticalLayout {
                    button {
                        text = "Choose Subject"
                        setOnClickListener {
                            selector("Choose Subject", allSubjects, { _, i ->
                                subjectId = RealmInteractor.getIdOfSubject(this@MainActivity, allSubjects[i])
                                allTopicsOfSubject = RealmInteractor.getAllTopicStringsOfSubject(this@MainActivity, subjectId)
                            })
                        }
                    }
                    button {
                        text = "Choose Topic"
                        setOnClickListener {
                            selector("Choose Topic", allTopicsOfSubject, {_, i->
                                topicId = RealmInteractor.getTopicIdFromString(this@MainActivity, allTopicsOfSubject[i])
                            })
                        }

                    }

                    etname = editText {
                        hint = "Enter Name Here"

                    }
                    etdefinition = editText {
                        hint = "Enter Defenetion Here"
                    }
                    positiveButton("Add", {
                        RealmInteractor.addDefinitionToDatabase(this@MainActivity, Definition(topicId, etname.text.toString(), etdefinition.text.toString()))

                    })

                }

            }
        }.show()
    }
}

