package ir.udmx.nikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.EXTRA_KEY_ID
import ir.udmx.nikestore.common.NikeActivity
import ir.udmx.nikestore.common.NikeCompletableObserver
import ir.udmx.nikestore.common.formatPrice
import ir.udmx.nikestore.data.Comment
import ir.udmx.nikestore.databinding.ActivityProductDetailBinding
import ir.udmx.nikestore.feature.product.comment.CommentListActivity
import ir.udmx.nikestore.services.ImageLoadingService
import ir.udmx.nikestore.view.scroll.ObservableScrollView
import ir.udmx.nikestore.view.scroll.ObservableScrollViewCallbacks
import ir.udmx.nikestore.view.scroll.ScrollState
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    val productDetailViewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    val commentAdapter = CommentAdapter() //چون دیپندنسی نداره نیازی به اینجکت نداره
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        productDetailViewModel.productLiveData.observe(this) {
            imageLoadingService.load(binding.productIv, it.image)
            binding.titleTv.text = it.title
            binding.currentPriceTv.text = formatPrice(it.price)
            binding.previousPriceTv.text = formatPrice(it.previous_price)
            binding.previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.toolbarTitleTv.text = it.title

        }

        productDetailViewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        productDetailViewModel.commentsLiveData.observe(this) {
            Timber.i(it.toString())
            commentAdapter.comments = it as ArrayList<Comment>

            if (it.size > 3) {
                binding.viewAllCommentsButton.visibility = View.VISIBLE
                binding.viewAllCommentsButton.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, productDetailViewModel.productLiveData.value!!.id)
                    })
                }
            }
        }
        initViews()
    }

    fun initViews() {
        /**
         * For init Views*/
        binding.commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.commentsRv.adapter = commentAdapter
        binding.commentsRv.isNestedScrollingEnabled = false
        binding.productIv.post {
            val productIvHeight = binding.productIv.height
            val toolbar = binding.toolbarView
            binding.observableScrollView.addScrollViewCallbacks(object :
                ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    binding.productIv.translationY = scrollY.toFloat() / 2
                    Timber.i("ProductIv height is -> $productIvHeight")
                    Timber.i("scrollY is -> $scrollY")
                }

                override fun onDownMotionEvent() {

                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {

                }
            })
        }

        binding.addToCartBtn.setOnClickListener {
            productDetailViewModel.onAddToCartBtn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.success_addToCart))
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}