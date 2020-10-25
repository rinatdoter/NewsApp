package kg.codingtask.newstestapp.extensions

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

fun ImageView.loadWithCrop(url: String){
    Glide.with(this.context).load(url).centerCrop().into(this)
}

fun ImageView.load(url: String){
    Glide.with(this.context).load(url).into(this)
}

fun TextView.underline(){
    this.paintFlags = this.paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun String.formatAsDate(): String{
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return  sdf.format(sdf.parse(this))
}