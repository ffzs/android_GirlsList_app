package com.ffzs.imageapp.data.repository

import com.ffzs.imageapp.data.local.ImageDao
import com.ffzs.imageapp.data.web.ImageWebData
import com.ffzs.imageapp.utils.performGetOperation
import javax.inject.Inject

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:34
 */


class ImageRepository @Inject constructor(
    private val webDataSource: ImageWebData,
    private val localDataSource: ImageDao
) {

    fun getImage(id: String) = performGetOperation(
        databaseQuery = { localDataSource.getImage(id) },
        networkCall = { webDataSource.getImage() },
        saveCallResult = {
            if (it.data.isNotEmpty())
                localDataSource.insert(it.data[0])
        }
    )

    fun getImages() = performGetOperation(
        databaseQuery = { localDataSource.getAllImages() },
        networkCall = { webDataSource.getImages() },
        saveCallResult = { localDataSource.insertAll(it.data) }
    )
}