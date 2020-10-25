package kg.codingtask.newstestapp.data.db

import androidx.room.*
import kg.codingtask.newstestapp.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Query("SELECT EXISTS(SELECT * FROM Article WHERE url = :url)")
    fun isArticleExist(url : String) : Boolean

    @Query("SELECT * FROM Article")
    fun getAllArticles(): List<Article>

    @Delete
    fun delete(article: Article)
}