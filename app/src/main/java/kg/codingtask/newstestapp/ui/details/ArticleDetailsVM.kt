package kg.codingtask.newstestapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.codingtask.newstestapp.models.Article
import kg.codingtask.newstestapp.repo.NewsRepository
import kotlinx.coroutines.launch

class ArticleDetailsVM(private val newsRepo: NewsRepository): ViewModel() {

    lateinit var article: Article
    var position: Int = -1

    var _isArticleSaved = MutableLiveData<Boolean>()

    val isArticleSaved: LiveData<Boolean>
    get() = _isArticleSaved

    fun checkIfPresent() {
        viewModelScope.launch {
            _isArticleSaved.postValue(newsRepo.getArticleByUrl(article.url))
        }
    }

    fun saveArticle(){
        viewModelScope.launch {
            newsRepo.saveArticle(article.apply { isBookMarked = true })
            _isArticleSaved.postValue(true)
        }
    }

    fun deleteArticle(){
        viewModelScope.launch {
            newsRepo.deleteArticle(article)
            _isArticleSaved.postValue(false)
        }
    }

}