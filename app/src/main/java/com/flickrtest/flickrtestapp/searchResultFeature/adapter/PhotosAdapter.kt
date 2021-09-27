package com.flickrtest.flickrtestapp.searchResultFeature.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flickrtest.domain.model.Photo
import com.flickrtest.flickrtestapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_photo_view.view.*


class PhotosAdapter(val photos: MutableList<Photo> = mutableListOf()) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.custom_photo_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) {
            Picasso.get()
                .load(photo.url)
                .resize(400, 400)
                .centerCrop()
                .into(itemView.imageView)
        }
    }
}