package com.example.instauser.ImageSlider


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instauser.R

class BannerAdapter : RecyclerView.Adapter<BannerViewHolder>() {

    private val bannerListItem = ArrayList<BannerModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item_layout, parent, false)

        return BannerViewHolder(view)
    }

    fun setData(items: List<BannerModel>) {
        bannerListItem.clear()
        bannerListItem.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return bannerListItem.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val bannerItem = bannerListItem[position]
        holder.bind(bannerItem)
    }
}

class BannerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val bannerImageView: ImageView = view.findViewById(R.id.banner_image_view)

    fun bind(bannerItem: BannerModel) {
        Glide.with(view.context)
                .load(bannerItem.imageurl)
                .into(bannerImageView)
    }
}