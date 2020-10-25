package kg.codingtask.newstestapp.ui.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kg.codingtask.newstestapp.R
import kg.codingtask.newstestapp.extensions.formatAsDate
import kg.codingtask.newstestapp.extensions.load
import kg.codingtask.newstestapp.extensions.underline
import kg.codingtask.newstestapp.models.Article
import kotlinx.android.synthetic.main.activity_article_details.*
import org.koin.android.viewmodel.ext.android.getViewModel

class ArticleDetailsActivity : AppCompatActivity() {

    lateinit var vm: ArticleDetailsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)
        vm = getViewModel(ArticleDetailsVM::class)
        parseDataFromIntent()
        vm.checkIfPresent()
        setupViews()
        subscribeToLiveData()
    }

    private fun parseDataFromIntent(){
        vm.article = intent.getSerializableExtra(Article::class.java.canonicalName) as Article
        vm.position = intent.getIntExtra(Int::class.java.canonicalName,-1)
    }

    private fun setupViews() {
        /** Можно было через DataBinding*/
        vm.article.run {
            iv_article.load(urlToImage)
            tv_title.text = title
            tv_author.text = author
            tv_date.text = publishedAt.formatAsDate()
            tv_source.text = source.name
            tv_content.text = content
            tv_link.run {
                text = source.name
                underline()
                setOnClickListener { startBrowser() }
            }
        }

        button.setOnClickListener {
            vm.isArticleSaved.value?.let {
                when(it){
                    true -> vm.deleteArticle()
                    false -> vm.saveArticle()
                }
            }
        }
    }

    private fun startBrowser(){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(vm.article.url))
        startActivity(browserIntent)
    }

    private fun subscribeToLiveData(){
        vm.isArticleSaved.observe(this, Observer {
            when(it){
                true -> button.text = getString(R.string.delete)
                false -> button.text = getString(R.string.save)
            }
        })
    }

    override fun onBackPressed() {
        if(vm.isArticleSaved.value == false){
            val intent = Intent().apply {
                putExtra(Int::class.java.canonicalName, vm.position)
                putExtra(Article::class.java.canonicalName,vm.article)
            }
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        else super.onBackPressed()
    }

    companion object {
        const val CODE = 322

        fun start(context: Context?, article: Article) {
            val intent = Intent(context, ArticleDetailsActivity::class.java).apply {
                putExtra(Article::class.java.canonicalName, article)
            }
            context?.startActivity(intent)
        }

        fun startForResult(context: Activity, article: Article, position: Int?){
            val intent = Intent(context, ArticleDetailsActivity::class.java).apply {
                putExtra(Article::class.java.canonicalName, article)
                putExtra(Int::class.java.canonicalName, position)
            }
            context.startActivityForResult(intent, CODE)
        }
    }
}