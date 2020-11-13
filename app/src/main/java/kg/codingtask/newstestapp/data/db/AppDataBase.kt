package kg.codingtask.newstestapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.codingtask.newstestapp.models.Article

@Database(
    entities = [Article::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ModelConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        const val dataBaseName = "sampleDb"
    }
}