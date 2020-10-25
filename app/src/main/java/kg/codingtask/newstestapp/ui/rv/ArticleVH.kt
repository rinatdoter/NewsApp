package kg.codingtask.newstestapp.ui.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kg.codingtask.newstestapp.extensions.loadWithCrop
import kg.codingtask.newstestapp.models.Article
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleVH(itemView: View) : RecyclerView.ViewHolder(itemView){

    lateinit var item: Article
    var index: Int = -1

    fun bind(item: Article,index: Int){
        this.item = item
        this.index = index
        itemView.run {
            tv_title.text = item.title
            imageView.loadWithCrop(item.urlToImage)
            tv_delete.visibility = if(item.isBookMarked == true) View.VISIBLE else View.INVISIBLE
        }
    }
}