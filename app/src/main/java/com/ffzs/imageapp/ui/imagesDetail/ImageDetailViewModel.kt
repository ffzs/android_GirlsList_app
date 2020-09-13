package com.ffzs.imageapp.ui.imagesDetail


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ffzs.imageapp.data.entities.Image
import com.ffzs.imageapp.data.repository.ImageRepository
import com.ffzs.imageapp.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * @author: ffzs
 * @Date: 20-9-12 下午6:39
 */
class ImageDetailViewModel  @ViewModelInject constructor(
    @ApplicationContext private val repository: ImageRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _image = _id.switchMap { id ->
        repository.getImage(id)
    }
    val image: LiveData<Resource<Image>> = _image

    fun start(id: String) {
        _id.value = id
    }

}