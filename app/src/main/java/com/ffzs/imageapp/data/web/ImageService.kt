package com.ffzs.imageapp.data.web

import com.ffzs.imageapp.data.entities.Image
import com.ffzs.imageapp.data.entities.ImageList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:37
 */


interface ImageService {

    @GET("random/category/{category}/type/{type}/count/{count}")
    suspend fun getInfo(
        @Path("category") category: String,
        @Path("type") type: String,
        @Path("count") count: Int
    ) :Response<List<Image>>


    @GET("random/category/Girl/type/Girl/count/20")
    suspend fun getImages() : Response<ImageList>

    @GET("random/category/Girl/type/Girl/count/1")
    suspend fun getImage() : Response<ImageList>

}