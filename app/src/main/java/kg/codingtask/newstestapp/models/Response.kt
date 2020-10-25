package kg.codingtask.newstestapp.models

import com.google.gson.annotations.SerializedName

data class Response(
    var status: Status,
    @SerializedName("code")
    var errorCode: String?,
    @SerializedName("message")
    var errorMessage: String?,
    var totalResults: Int,
    var articles: List<Article>
)

enum class Status{
    @SerializedName("ok")
    OK,
    @SerializedName("error")
    ERROR
}