package kg.codingtask.newstestapp.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kg.codingtask.newstestapp.R
import kg.codingtask.newstestapp.models.Article
import kg.codingtask.newstestapp.models.Event
import kg.codingtask.newstestapp.ui.details.ArticleDetailsActivity
import kg.codingtask.newstestapp.ui.rv.ArticlesAdapter
import kotlinx.android.synthetic.main.fragment_articles.*
import org.koin.android.viewmodel.ext.android.getViewModel

abstract class ArticlesBaseFragment : Fragment(R.layout.fragment_articles),
    ArticlesAdapter.Listener {

    abstract fun refresh()
    abstract fun load()

    lateinit var vm: ArticlesVM
    private lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = getViewModel(ArticlesVM::class)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        subscribeToLiveData()
        load()
    }

    private fun setupViews() {
        adapter = ArticlesAdapter(this)
        rv_articles.adapter = adapter
        rv_articles.itemAnimator = null

        rv_articles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !adapter.isLoading) {
                    adapter.showLoader()
                    load()
                }
            }
        })

        swipe_refresh.setOnRefreshListener {
            adapter.clearItems()
            refresh()
        }
    }

    private fun subscribeToLiveData() {
        vm.newsArticles.observe(viewLifecycleOwner, Observer {
            adapter.hideLoader()
            adapter.addNewItems(it)
            hideSwipeRefresh()
        })

        vm.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.Error -> {
                    adapter.hideLoader()
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is Event.HideLoader -> adapter.hideLoader()
            }
            hideSwipeRefresh()
        })
    }

    private fun hideSwipeRefresh() {
        swipe_refresh.run { if (isRefreshing) isRefreshing = false }
    }

    override fun onClick(item: Article, position: Int?) {
        ArticleDetailsActivity.start(context, item)
    }
}