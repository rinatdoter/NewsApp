package kg.codingtask.newstestapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kg.codingtask.newstestapp.R
import kg.codingtask.newstestapp.ui.everything.EveryThingFragment
import kg.codingtask.newstestapp.ui.headlines.HeadlinesFragment
import kg.codingtask.newstestapp.ui.saved.SavedArticlesActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = getViewModel(MainVm::class)
        setup()
    }

    private fun setup() {
        nav_view.setOnNavigationItemSelectedListener {
            loadFragment(it.itemId)
            true
        }
        nav_view.selectedItemId = R.id.navigation_headlines
        fab.setOnClickListener { SavedArticlesActivity.start(this) }
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        val fragment = geFragment(tag, itemId)

        fragment?.let {
            val transaction = supportFragmentManager.beginTransaction()

            hideLastFragment(transaction)
            showNewFragment(fragment, transaction, tag)

            transaction.commit()
            viewModel.lastActiveFragmentTag = tag
        }
    }

    private fun geFragment(tag: String, itemId: Int): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.navigation_headlines -> HeadlinesFragment()
            R.id.navigation_everything -> EveryThingFragment()
            else -> null
        }
    }

    private fun showNewFragment(
        fragment: Fragment,
        transaction: FragmentTransaction,
        tag: String
    ) {
        when (!fragment.isAdded) {
            true -> transaction.add(R.id.fragment_container, fragment, tag)
            else -> transaction.show(fragment)
        }
    }

    private fun hideLastFragment(transaction: FragmentTransaction) {
        viewModel.lastActiveFragmentTag?.let { tag ->
            supportFragmentManager.findFragmentByTag(tag)?.let { lastFragment ->
                transaction.hide(lastFragment)
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentByTag(R.id.navigation_headlines.toString())?.let {
            when {
                it.isVisible -> super.onBackPressed()
                else -> nav_view.selectedItemId = R.id.navigation_headlines
            }
        }
    }
}