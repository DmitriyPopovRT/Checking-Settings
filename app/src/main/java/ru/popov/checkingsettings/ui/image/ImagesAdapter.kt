package ru.popov.checkingsettings.ui.image

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.popov.checkingsettings.data.Image

class ImagesAdapter(
    onDeleteImage: (name: String) -> Unit,
    onClickImage: (name: String) -> Unit
) : AsyncListDifferDelegationAdapter<Image>(ImageDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(ImageAdapterDelegate(onDeleteImage, onClickImage))
    }

    class ImageDiffUtilCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

}