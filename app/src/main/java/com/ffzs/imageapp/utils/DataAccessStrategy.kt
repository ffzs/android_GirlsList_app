package com.ffzs.imageapp.utils

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午3:55
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ffzs.imageapp.utils.Resource.Status.*
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }