package kg.codingtask.newstestapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.codingtask.newstestapp.models.Source

class ModelConverter{
    @TypeConverter
    fun fromRoleToJson(role: Source) = Gson().toJson(role)!!

    @TypeConverter
    fun fromJsonToRole(json: String?) =
        Gson().fromJson<Source>(json, object : TypeToken<Source?>() {}.type)!!
}

