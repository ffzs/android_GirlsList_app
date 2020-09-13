package com.ffzs.imageapp.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.ffzs.imageapp.data.local.ImageDao
import com.ffzs.imageapp.data.local.ImageDatabase
import com.ffzs.imageapp.data.repository.ImageRepository
import com.ffzs.imageapp.data.web.ImageService
import com.ffzs.imageapp.data.web.ImageWebData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午2:34
 */

@Module  // 注释该对象以指示我们将从此处获取依赖项
@InstallIn(ApplicationComponent::class)  // 组件的层级
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton  // 唯一实例
    @Provides  // 通过 @Provides注入， 还可以通过@Binds，使用场景不同
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://gank.io/api/v2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideImageService(retrofit: Retrofit): ImageService = retrofit.create(ImageService::class.java)

    @Singleton
    @Provides
    fun provideImageRemoteDataSource(imageService: ImageService):ImageWebData = ImageWebData(imageService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context):ImageDatabase = ImageDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideImageDao(db: ImageDatabase):ImageDao = db.imageDao

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ImageWebData,
                          localDataSource: ImageDao) =
        ImageRepository(remoteDataSource, localDataSource)
}