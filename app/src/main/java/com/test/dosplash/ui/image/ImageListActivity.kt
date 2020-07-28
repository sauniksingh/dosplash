package com.test.dosplash.ui.image

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.test.dosplash.AppExecutors
import com.test.dosplash.R
import com.test.dosplash.base.BaseActivity
import com.test.dosplash.databinding.LayoutImageShimmerBinding
import com.test.dosplash.listener.ChildListener
import com.test.dosplash.model.UnsplashImage
import com.test.dosplash.util.ToastUtils
import com.test.dosplash.viewmodel.ImageListViewModel
import com.test.dosplash.viewmodel.ImageViewModelFactory
import kotlinx.android.synthetic.main.activity_image_list.*

class ImageListActivity : BaseActivity(), ChildListener {
    private lateinit var mAdapter: ImageAdapter
    private lateinit var mImageListViewModel: ImageListViewModel
    private var currentPage = 1
    private val pageSize = 9
    private var isLoading = false
    private var images = ArrayList<UnsplashImage>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val isLastPage = false
    val appExecutor = AppExecutors.getInstance()
    private lateinit var shimmerBinding: LayoutImageShimmerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        initView()
        initRecyclerView()
        setViewModel()
        hitApi()
    }

    override fun onImageClick(unsplashImage: UnsplashImage) {
    }

    private fun initRecyclerView() {
        val viewPreloader =
            ViewPreloadSizeProvider<String>()
        mAdapter =
            ImageAdapter(images, this, initGlide(), viewPreloader)
        val preloader: RecyclerViewPreloader<String> = RecyclerViewPreloader<String>(
            Glide.with(this),
            mAdapter,
            viewPreloader,
            30
        )
        linearLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        imageList.apply {
            layoutManager = linearLayoutManager
            addOnScrollListener(preloader)
            adapter = mAdapter
            addOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    private fun setViewModel() {
        mImageListViewModel =
            ViewModelProvider(this, ImageViewModelFactory()).get(ImageListViewModel::class.java)
        mImageListViewModel.setGenericListeners(getErrorObserver(), getFailureResponseObserver())
        mImageListViewModel.imageListData?.observe(
            this,
            androidx.lifecycle.Observer { result: ArrayList<UnsplashImage>? ->
                isLoading = false
                hideAndStopShimmerView(false)
                swipeRefresh.isRefreshing = false
                mAdapter.hideLoading()
                result?.apply {
                    if (currentPage <= 1) {
                        images = result
                    } else {
                        images.addAll(result)
                    }
                    updateData()
                }
            })
    }

    private fun hitApi() {
        if (!isLoading) {
            hideAndStopShimmerView(true)
            getDataFromServer()
        }
    }

    private fun hideAndStopShimmerView(isShow: Boolean) {
        if (isShow) {
            imageList.visibility = View.GONE
            shimmer_frame.visibility = View.VISIBLE
            shimmerBinding.shimmerViewContainer.startShimmer()
            shimmerBinding.shimmerViewContainer.visibility = View.VISIBLE
        } else {
            imageList.visibility = View.VISIBLE
            shimmer_frame.visibility = View.GONE
            shimmerBinding.shimmerViewContainer.stopShimmer()
            shimmerBinding.shimmerViewContainer.visibility = View.GONE
        }
    }

    private fun updateData() {
        appExecutor.mainThread().execute {
            mAdapter.setImages(images)
        }
//        if((coursesTags?.tags !=null && coursesTags?.tags?.isNotEmpty()!!) || selectedTags.isNotEmpty() ) {
//            rvTags.visibility = View.VISIBLE
//            hideEmptyView(binding.error.llEmptyviewContainer, binding.error.tvTryAgain)
//        } else {
//            rvTags.visibility = View.GONE
//            showEmptyView(AppConstants.EMPTY_VIEW_TYPES.NO_RECORD)
//        }
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = linearLayoutManager.childCount
                val totalItemCount: Int = linearLayoutManager.itemCount
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && (totalItemCount >= pageSize || images.isEmpty())
                    ) {
                        mAdapter.displayLoading()
                    }
                }
            }
        }

    private fun getDataFromServer() {
        if (isNetworkAvailable()) {
            appExecutor.diskIO().execute {
                mImageListViewModel.getImages(currentPage, pageSize)
            }
            isLoading = true
        } else {
            ToastUtils.showToast(this, R.string.no_internet)
        }
    }

    override fun onLoadMore() {
        currentPage += 1
        getDataFromServer()
    }

    private fun initView() {
        shimmerBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_image_shimmer,
            shimmer_frame.parent as ViewGroup,
            true
        )
        hideAndStopShimmerView(false)
    }
}