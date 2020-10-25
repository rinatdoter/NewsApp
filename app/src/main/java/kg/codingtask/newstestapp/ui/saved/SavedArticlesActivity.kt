package kg.codingtask.newstestapp.ui.saved

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kg.codingtask.newstestapp.R
import kg.codingtask.newstestapp.models.Article
import kg.codingtask.newstestapp.ui.details.ArticleDetailsActivity
import kg.codingtask.newstestapp.ui.rv.ArticlesAdapter
import kotlinx.android.synthetic.main.activity_saved_articles.*
import org.koin.android.viewmodel.ext.android.getViewModel

class SavedArticlesActivity : AppCompatActivity(), ArticlesAdapter.Listener {

    private lateinit var vm: SavedArticlesVM
    lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_articles)
        vm = getViewModel(SavedArticlesVM::class)
        setupViews()
        subscribeToLiveData()
        vm.getAllArticles()
    }

    private fun subscribeToLiveData() {
        vm.savedArticles.observe(this, Observer {
            adapter.setItems(it)
        })
    }

    private fun setupViews() {
        adapter = ArticlesAdapter(this)
        rv_saved_articles.adapter = adapter
    }

    override fun onClick(item: Article, position: Int?) {
        ArticleDetailsActivity.startForResult(this, item, position)
    }

    override fun onDeleteClick(item: Article) {
        vm.deleteArticle(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == ArticleDetailsActivity.CODE) {
            data?.let {
                val index = it.getIntExtra(Int::class.java.canonicalName, -1)
                val article = it.getSerializableExtra(Article::class.java.canonicalName) as Article

                if (index != -1) adapter.removeItem(index, article)
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        fun start(context: Context?) {
            context?.let {
                it.startActivity(Intent(it, SavedArticlesActivity::class.java))
            }
        }
    }
}