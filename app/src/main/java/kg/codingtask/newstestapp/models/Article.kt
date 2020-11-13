package kg.codingtask.newstestapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Article(
    @PrimaryKey
    var url: String = "",
    var source: Source,
    var author: String?="",
    var title: String? = "",
    var description: String? = "",
    var urlToImage: String? = " ",
    var publishedAt: String? = "",
    var content: String? = "",
    var isBookMarked: Boolean? = false
): Serializable

data class Source(
    var id: String? = "",
    var name: String? = ""
): Serializable