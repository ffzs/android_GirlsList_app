package com.ffzs.imageapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ffzs.imageapp.data.entities.Image
import com.ffzs.imageapp.data.entities.ImageList

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:28
 */
@Dao
interface ImageDao {

    @Query("SELECT * FROM image_data_table ORDER BY views DESC")
    fun getAllImages() : LiveData<List<Image>>

    @Query("SELECT * FROM image_data_table WHERE _id = :id")
    fun getImage(id: String): LiveData<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(image_data_table: List<Image>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image)
}