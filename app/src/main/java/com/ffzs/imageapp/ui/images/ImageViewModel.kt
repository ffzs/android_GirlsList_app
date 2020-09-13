package com.ffzs.imageapp.ui.images

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ffzs.imageapp.data.repository.ImageRepository

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:22
 */
class ImageViewModel @ViewModelInject constructor(
    repository: ImageRepository
) : ViewModel() {
    val images = repository.getImages()
}
