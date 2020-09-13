package com.ffzs.imageapp.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ffzs.imageapp.R
import com.ffzs.imageapp.databinding.ImageFragmentBinding
import com.ffzs.imageapp.utils.Resource
import com.ffzs.imageapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author: ffzs
 * @Date: 2020/9/12 下午2:08
 */

@AndroidEntryPoint
class ImageFragment: Fragment(), ImageAdapter.ImageItemListener {

    private var binding: ImageFragmentBinding by autoCleared()
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    // 设置RecyclerView 的 manager 和 adapter
    private fun setupRecyclerView() {
        adapter = ImageAdapter(this)
        binding.imageRv.layoutManager = LinearLayoutManager(requireContext())
        binding.imageRv.adapter = adapter
    }

    // 通过observe接收publisher推送过来的数据， 并通过状态进行相应处理
    private fun setupObservers() {
        viewModel.images.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    // 这里复写了 ImageAdapter.ImageItemListener 接口的 onClickedImage 方法， holder中执行传入 imageId
    override fun onClickedImage(imageId: String) {
        // 通过 NavController进行绑定， 生成action的绑定， 并生成key为id的Bundle， 之后fragment使用
        findNavController().navigate(
            R.id.action_imageFragment_to_imageDetailFragment,
            bundleOf("id" to imageId)
        )
    }
}