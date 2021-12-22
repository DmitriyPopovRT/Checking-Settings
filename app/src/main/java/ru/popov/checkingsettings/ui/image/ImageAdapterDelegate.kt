package ru.popov.checkingsettings.ui.image

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.popov.checkingsettings.R
import ru.popov.checkingsettings.data.Image
import ru.popov.checkingsettings.databinding.ItemImageBinding
import ru.popov.checkingsettings.utils.Utils.inflate

class ImageAdapterDelegate(
    private val onDeleteImage: (name: String) -> Unit,
    private val onClickImage: (name: String) -> Unit
) : AbsListItemAdapterDelegate<Image, Image, ImageAdapterDelegate.Holder>() {

    override fun isForViewType(item: Image, items: MutableList<Image>, position: Int): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return parent.inflate(ItemImageBinding::inflate).let { Holder(it, onDeleteImage, onClickImage) }
    }

    override fun onBindViewHolder(item: Image, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        private val binding: ItemImageBinding,
        onDeleteImage: (name: String) -> Unit,
        onClickImage: (name: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentImageName: String? = null
        private var currentImageClick: String? = null

        init {
            binding.deleteButton.setOnClickListener {
                currentImageName?.let(onDeleteImage)
            }
            binding.constraintItem.setOnClickListener {
                currentImageClick?.let(onClickImage)
            }
        }

        fun bind(item: Image) {
            currentImageName = item.name
            currentImageClick = item.file.toUri().toString()

            with(binding) {
                nameTextView.text = item.name
                sizeTextView.text = "${item.size / 1024} КБ"
                Glide.with(imageView)
                    .load(item.file)
                    .placeholder(R.drawable.ic_image)
                    .into(imageView)
            }
        }
    }
}