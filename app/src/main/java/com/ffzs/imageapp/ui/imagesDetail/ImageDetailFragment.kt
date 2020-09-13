package com.ffzs.imageapp.ui.imagesDetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ffzs.imageapp.data.entities.Image
import com.ffzs.imageapp.databinding.ImageDetailFragmentBinding
import com.ffzs.imageapp.utils.Resource
import com.ffzs.imageapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午2:08
 */


@AndroidEntryPoint
class ImageDetailFragment: Fragment() {

    private var binding: ImageDetailFragmentBinding by autoCleared()   // 代理模式 初始化清除 value
    private val viewModel: ImageDetailViewModel by viewModels()   // viewModels返回的是一个ViewModelLazy对象 实现lazy

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImageDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // arguments:Bundle类型，通过key，value形式传递数据 detailFragment中接收id fragment传入id
        // 这里fragment传入id 通过viewModel获取到信息放入 image中
        arguments?.getString("id")?.let { viewModel.start(it) }
        // 对viewModel中的image对象observe，通过bindImage函数设置数据
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.image.observe(viewLifecycleOwner, {
            when (it.status) {
                /**
                 * 访问成功 显示image
                 * View.GONE 不显示且不占用空间
                 * View.INVISIBLE 不显示但是占用空间
                 */
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> bindImage(it1) }
                    binding.progressBar.visibility = View.GONE
                    binding.imageCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                // loading 状态 progressBar VISIBLE
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.imageCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindImage(image: Image) {
        binding.desc.text = image.desc
        Glide.with(binding.root)
            .load(image.url)
            .override(1800)
            .into(binding.image)
    }
}