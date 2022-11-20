package com.example.salttestanandasatriyo.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.salttestanandasatriyo.R
import com.example.salttestanandasatriyo.common.dateFormat
import com.example.salttestanandasatriyo.common.loadImage
import com.example.salttestanandasatriyo.data.model.Article
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_news,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class NewsViewHolder ( itemView: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(itemView) {



        fun bind(item: Article) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            articleImage.loadImage(item.urlToImage)
            titleTxt.text = item.title
            authorNameTxt.text = "By ${item.author} "
            dateTxt.text = "${dateFormat(item.publishedAt)}"
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Article)
    }
}

