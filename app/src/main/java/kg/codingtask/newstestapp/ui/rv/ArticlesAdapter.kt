package kg.codingtask.newstestapp.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.codingtask.newstestapp.R
import kg.codingtask.newstestapp.models.Article
import kotlinx.android.synthetic.main.item_article.view.*


class ArticlesAdapter(private val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = arrayListOf<Any>()
    var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Type.ARTICLE ->{
                createArticleVH(parent)
            }
            Type.EMPTY -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
                EmptyVH(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
                LoaderVH(view)
            }
        }
    }

    private fun createArticleVH(parent: ViewGroup): ArticleVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        val holder = ArticleVH(view)
        holder.itemView.setOnClickListener {
            listener.onClick(holder.item, holder.index)
        }

        holder.itemView.tv_delete.setOnClickListener {
            listener.onDeleteClick(holder.item)
            items.remove(holder.item)
            notifyItemRemoved(holder.index)

        }
        return holder
    }

    fun removeItem(index: Int,article: Article){
        items.remove(article)
        notifyItemRemoved(index)
    }

    override fun getItemCount() = if(items.count() == 0) 1 else items.count()

    override fun getItemViewType(position: Int): Int {
        return if (items.count() == 0)
            Type.EMPTY
        else
            when (items[position]) {
                is Article -> Type.ARTICLE
                else -> Type.LOADER
            }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ArticleVH -> holder.bind(items[position] as Article,position)
            else -> {}
        }
    }

    fun addNewItems(newsItems: List<Article>) {
        items.addAll(newsItems)
        notifyItemRangeInserted(items.count() - newsItems.count(), items.count())
    }

    fun showLoader(){
        items.add(Any())
        notifyItemInserted(items.lastIndex)
        isLoading = true
    }

    fun hideLoader(){
        if(items.isNotEmpty()){
            items.removeAt(items.lastIndex)
            notifyItemRemoved(items.lastIndex)
        }
        isLoading = false
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun setItems(items: List<Article>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    object Type {
        const val EMPTY = 2
        const val ARTICLE = 1
        const val LOADER = 0
    }

    interface Listener{
        fun onClick(item: Article, position: Int? = -1)
        fun onDeleteClick(item: Article){}
    }
}
