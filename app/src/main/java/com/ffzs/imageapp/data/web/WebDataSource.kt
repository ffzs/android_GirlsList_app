package com.ffzs.imageapp.data.web

import com.ffzs.imageapp.data.entities.ImageList
import com.ffzs.imageapp.utils.Resource
import retrofit2.Response
import timber.log.Timber

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:45
 */
abstract class WebDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body as ImageList
                // 将api获取的图片信息中http换为https不然无法完成跳转
                body.data.map {
                    it.url = it.url.replace("http://", "https://")
                }
                Timber.i(body.toString())
                return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.error("访问出错: $message")
    }
}