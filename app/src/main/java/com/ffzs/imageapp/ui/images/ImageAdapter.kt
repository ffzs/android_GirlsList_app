package com.ffzs.imageapp.ui.images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ffzs.imageapp.data.entities.Image
import com.ffzs.imageapp.databinding.ItemImageBinding

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午4:24
 */
class ImageAdapter(private val listener: ImageItemListener) :
    RecyclerView.Adapter<ImageViewHolder>() {

    interface ImageItemListener {
        fun onClickedImage(imageId: String)
    }

    private val items = ArrayList<Image>()

    fun setItems(items: ArrayList<Image>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemImageBinding =
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(items[position])

}


class ImageViewHolder(
    private val itemBinding: ItemImageBinding,
    private val listener: ImageAdapter.ImageItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var image: Image

    // 激活组建onclick
    init {
        itemBinding.root.setOnClickListener(this)
    }

    // 设置组建展示内容
    fun bind(item: Image) {
        this.image = item
        itemBinding.name.text = item.title
        itemBinding.speciesAndStatus.text = """${item.likeCounts}人喜欢 - ${item.views}人查看"""
        Glide.with(itemBinding.root)
            .load(item.url)
            .override(200, 200)
            .centerCrop()
            .into(itemBinding.image);
    }

    override fun onClick(v: View?) {
        listener.onClickedImage(image._id)
    }
}


