package com.ffzs.imageapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:23
 */
@Entity(tableName = "image_data_table")
data class Image (

    @PrimaryKey
    val _id:String,
    val author:String,
    var url:String,
    val title:String,
    val desc:String,
    val likeCounts:Long,
    val views:Long,
)