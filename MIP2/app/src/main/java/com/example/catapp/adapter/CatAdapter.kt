package com.example.catapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.catapp.R
import com.example.catapp.model.CatImage

class CatAdapter : ListAdapter<CatImage, CatAdapter.CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cat_image, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val catImageView: ImageView = itemView.findViewById(R.id.catImageView)

        fun bind(catImage: CatImage) {
            Glide.with(itemView.context)
                .load(catImage.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(catImageView)
        }
    }
}

class CatDiffCallback : DiffUtil.ItemCallback<CatImage>() {
    override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage): Boolean {
        return oldItem == newItem
    }
}
