package studybuddy.aayushf.studybuddy

import adapters.MainTabsPager
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar

import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import org.jetbrains.anko.startActivityForResult
import databaseandstorage.RealmInteractor
import databaseandstorage.StorageInteractor
import fragments.ItemFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import objects.Constant
import objects.Definition
import objects.Topic
import org.jetbrains.anko.*
import org.jetbrains.anko.selector
import org.polaric.colorful.CActivity
import org.polaric.colorful.ColorPickerDialog
import org.polaric.colorful.Colorful
import java.util.*

class MainActivity : CActivity() , AnkoLogger{

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mMainTabsPager: MainTabsPager? = null
    private var mTTSObject:TextToSpeech? = null
    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null
    companion object {
        private val REQUEST_DRAWING = 0
    }

    private var drawingURL:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colorful.defaults()
                .primaryColor(Colorful.ThemeColor.RED)
                .accentColor(Colorful.ThemeColor.BLUE)
                .translucent(false)
                .dark(true)
        Colorful.init(this)
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
        mTTSObject = TextToSpeech(this, {t->
            if (t!=TextToSpeech.ERROR){
                mTTSObject?.language = Locale.US
            }

        })

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


        when (id) {
            R.id.action_add_subject -> addSubject()
            R.id.action_add_topic -> addTopic()
            R.id.action_theme -> showThemeChooser()
            R.id.action_drawing -> startActivity<DrawingActivity>()
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
        selector("Select Type Of Item To Add", listOf("Definition", "Constant"), { _, i ->
            if(i==0){
                addDefinition()
            } else if (i == 1) {
                addConstant()
            }

        })
    }

    private fun addConstant() {
        drawingURL = null
        alert {
            customView {
                title = "Add A Defenetion"
                var etname: EditText
                var etdenoted: EditText
                var etvalue: EditText
                var btnTopic: Button
                val allSubjects = RealmInteractor.getAllSubjectStrings(this@MainActivity)
                var subjectId: Long = 0
                var allTopicsOfSubject: List<String> = listOf()
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
                            selector("Choose Topic", allTopicsOfSubject, { _, i ->
                                topicId = RealmInteractor.getTopicIdFromString(this@MainActivity, allTopicsOfSubject[i])
                            })
                        }

                    }
                    button {
                        text = "Add a scribble"
                        setOnClickListener({
                            val i = Intent(this@MainActivity, DrawingActivity::class.java)
                            startActivityForResult(i, REQUEST_DRAWING)
                        })
                    }


                    etname = editText {
                        hint = "Enter Name Here"

                    }
                    etdenoted = editText {
                        hint = "Enter Denotion Here"
                    }
                    etvalue = editText {
                        hint = "Enter Value Here"

                    }
                    positiveButton("Add", {
                        RealmInteractor.addItemToDatabase(this@MainActivity, Constant(topicId, etname.text.toString(), etdenoted.text.toString(), etvalue.text.toString(), scribblePath = drawingURL))
                        updateTabs()
                    })

                }

            }
        }.show()

    }

    fun addDefinition(){
        drawingURL = null
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
                    button{
                        text = "Add a scribble"
                        setOnClickListener({
                            val i = Intent(this@MainActivity, DrawingActivity::class.java)
                            startActivityForResult(i, REQUEST_DRAWING)
                        })
                    }


                    etname = editText {
                        hint = "Enter Name Here"

                    }
                    etdefinition = editText {
                        hint = "Enter Defenetion Here"
                    }
                    positiveButton("Add", {
                        RealmInteractor.addItemToDatabase(this@MainActivity, Definition(topicId, etname.text.toString(), etdefinition.text.toString(), scribblePath = drawingURL))
                        updateTabs()
                    })

                }

            }
        }.show()
    }
    fun showThemeChooser(){
        alert {
            customView {
                var primaryColor:Colorful.ThemeColor = Colorful.ThemeColor.BLUE
                var accentColor:Colorful.ThemeColor = Colorful.ThemeColor.AMBER

                verticalLayout {
                    val c = checkBox {
                        text = "Set Dark Theme?"
                    }
                    button {
                        text = "Choose Primary Colour"
                        setOnClickListener {
                            val cpd = ColorPickerDialog(this@MainActivity)
                            cpd.setOnColorSelectedListener { c->
                                primaryColor = c


                            }
                            cpd.show()
                        }
                    }
                    button {
                        text = "Choose Accent Colour"
                        setOnClickListener {
                            val cpd = ColorPickerDialog(this@MainActivity)
                            cpd.setOnColorSelectedListener { c->
                                accentColor = c


                            }
                            cpd.show()
                        }

                    }
                    positiveButton("Apply", {
                        Colorful.config(this@MainActivity).primaryColor(primaryColor).accentColor(accentColor).dark(c.isChecked).translucent(false).apply()
                        toast("Restart App For Changes To Apply")

                    })
                }
            }
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            drawingURL = data.getStringExtra("drawingPath")
            toast(drawingURL as String)
        }


    }
    fun onItemSelected(itemid:Long){
        alert{
            customView {
                title = "Show Scribble"
                var path: String? = null
                var item = RealmInteractor.getItem(this@MainActivity, itemid)
                when (item) {
                    is Definition -> path = item.scribblePath
                    is Constant -> path = item.scribblePath
                }
                imageView {

                    if(path != null)
                    imageBitmap = StorageInteractor.getBitmapFromFile(this@MainActivity, path!!)
                }
            }
        }.show()
        info("ShowDrawingCalled")
        val fragment = ItemFragment(itemid, this)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.bsflmain, fragment)
        ft.commit()
    }
    fun goToRevise(topicID:Long){
        val i = Intent(this@MainActivity, ReviseActivity::class.java)
        i.putExtra("topicID", topicID)
        startActivity(i)
    }
    fun speakItem(itemid: Long){
        mTTSObject!!.speak((RealmInteractor.getItem(this, itemid) as Definition).definition, TextToSpeech.QUEUE_FLUSH, null )

    }
}

