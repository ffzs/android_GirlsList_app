package com.ffzs.imageapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ffzs.imageapp.data.entities.Image

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:27
 */

@Database(entities = [Image::class], version = 4)
abstract class ImageDatabase :RoomDatabase(){
    abstract val imageDao: ImageDao

    companion object {
        @Volatile private var instance: ImageDatabase? = null

        fun getDatabase(context: Context): ImageDatabase =
            instance ?: synchronized(this) { instance ?: getInstance(context).also { instance = it } }

        private fun getInstance(appContext: Context) =
            Room.databaseBuilder(appContext, ImageDatabase::class.java, "image_data_table")
                .fallbackToDestructiveMigration()
                .build()
    }
}