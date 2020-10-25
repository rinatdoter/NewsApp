package kg.codingtask.newstestapp.data.network

import kg.codingtask.newstestapp.models.Constants

import kg.codingtask.newstestapp.models.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {

    /** В реальном проекте добавил бы interceptor для apiKey*/

    @GET("top-headlines")
    suspend fun fetchHeadlines(
        @Header("Authorization") token: String? = Constants.KEY,
        @Query("q") keyPhrase: String? = Constants.KEY_WORD,
        @Query("pageSize") pageSize: Int? = Constants.PAGE_SIZE,
        @Query("page") page: Int
    ): Response

    @GET("everything")
    suspend fun fetchEverything(
        @Header("Authorization") token: String? = Constants.KEY,
        @Query("q") keyPhrase: String? = Constants.KEY_WORD,
        @Query("pageSize") pageSize: Int? = Constants.PAGE_SIZE,
        @Query("page") page: Int
    ): Response
}