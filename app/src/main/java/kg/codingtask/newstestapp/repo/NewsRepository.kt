package kg.codingtask.newstestapp.repo

import kg.codingtask.newstestapp.data.db.AppDataBase
import kg.codingtask.newstestapp.data.network.NewsApi
import kg.codingtask.newstestapp.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsApi: NewsApi,
    private val db: AppDataBase
) {

    suspend fun fetchHeadlines(page: Int) =
        withContext(Dispatchers.IO) { newsApi.fetchHeadlines(page = page) }

    suspend fun fetchEverything(page: Int) =
        withContext(Dispatchers.IO) { newsApi.fetchEverything(page = page) }

    suspend fun saveArticle(article: Article) =
        withContext(Dispatchers.IO) {db.articleDao().insert(article)}

    suspend fun deleteArticle(article: Article) =
        withContext(Dispatchers.IO) {db.articleDao().delete(article)}

    suspend fun getAllSavedArticles() =
        withContext(Dispatchers.IO) {db.articleDao().getAllArticles()}

    suspend fun getArticleByUrl(url: String) =
        withContext(Dispatchers.IO) {db.articleDao().isArticleExist(url)}

}