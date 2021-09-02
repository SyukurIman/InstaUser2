package com.example.instauser.RecyclerViewItems


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instauser.R
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ItemsViewHolder>() {
    private val mData = ArrayList<ItemsView>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClick(itemsView: ItemsView)
    }

    fun setData(items: ArrayList<ItemsView>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItemsViewHolder {
        val mView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)

        return ItemsViewHolder(mView)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(mData[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(mData[holder.adapterPosition])}
    }

    inner class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemsView: ItemsView) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(itemsView.avatar)
                    .into(img_photo)
                text_name.text = itemsView.name
                typeAccount.text = itemsView.type
            }
        }
    }

}