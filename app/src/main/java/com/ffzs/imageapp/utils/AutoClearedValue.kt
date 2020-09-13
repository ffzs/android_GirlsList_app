package com.ffzs.imageapp.utils

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午4:07
 */
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A lazy property that gets cleaned up when the fragment's view is destroyed.
 *
 * Accessing this variable while the fragment's view is destroyed will throw NPE.
 */
class AutoClearedValue<T : Any>(val fragment: Fragment) : ReadWriteProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            _value = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: throw IllegalStateException(
            "value不存在"
        )
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)