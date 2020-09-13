package com.ffzs.imageapp.data.web

import javax.inject.Inject

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:43
 */


class ImageWebData @Inject constructor(
    private val imageService: ImageService,
) : WebDataSource() {

    suspend fun getImages() = getResult { imageService.getImages() }
    suspend fun getImage() = getResult { imageService.getImage() }
    suspend fun getInfo(
        category: String,
        type: String,
        count: Int,
    ) = getResult { imageService.getInfo(category, type, count) }
}