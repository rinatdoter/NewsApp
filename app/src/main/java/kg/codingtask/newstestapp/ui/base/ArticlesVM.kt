package kg.codingtask.newstestapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.codingtask.newstestapp.models.Article
import kg.codingtask.newstestapp.models.Event
import kg.codingtask.newstestapp.models.Response
import kg.codingtask.newstestapp.repo.NewsRepository
import kotlinx.coroutines.launch

class ArticlesVM(private val newsRepo: NewsRepository): ViewModel() {

    private val _newArticles = MutableLiveData<List<Article>>()
    private val _event = MutableLiveData<Event>()

    val newsArticles: LiveData<List<Article>>
        get() = _newArticles

    val event: LiveData<Event>
        get() = _event

    private var pageNum = 1

    fun refreshHeadlines(){
        pageNum = 1
        loadArticles()
    }

    fun refreshEveryThing(){
        pageNum = 1
        loadArticles()
    }

    fun loadArticles(){
        load { newsRepo.fetchHeadlines(pageNum) }
    }

    fun loadAllArticles(){
        load { newsRepo.fetchEverything(pageNum) }
    }

    private fun load(loadingFun: suspend (page: Int) -> Response){
        viewModelScope.launch {
            try{
                _newArticles.postValue(loadingFun(pageNum).articles)
                pageNum++
            }catch (e: Exception){
                _event.postValue(Event.Error(e.message ?: "error"))
            }
        }
    }
}