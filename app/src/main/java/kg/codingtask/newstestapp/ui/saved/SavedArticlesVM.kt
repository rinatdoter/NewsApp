package kg.codingtask.newstestapp.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.codingtask.newstestapp.models.Article
import kg.codingtask.newstestapp.repo.NewsRepository
import kotlinx.coroutines.launch

class SavedArticlesVM(private val newsrepo: NewsRepository): ViewModel() {

    private val _savedArticles =  MutableLiveData<List<Article>>()

    val savedArticles: LiveData<List<Article>>
    get() = _savedArticles

    fun deleteArticle(article: Article){
        viewModelScope.launch {
            newsrepo.deleteArticle(article)
        }
    }

    fun getAllArticles(){
        viewModelScope.launch {
            _savedArticles.postValue(newsrepo.getAllSavedArticles())
        }
    }
}