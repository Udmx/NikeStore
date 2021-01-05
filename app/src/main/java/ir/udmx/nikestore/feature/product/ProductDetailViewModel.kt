package ir.udmx.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.udmx.nikestore.common.EXTRA_KEY_DATA
import ir.udmx.nikestore.common.NikeSingleObserver
import ir.udmx.nikestore.common.NikeViewModel
import ir.udmx.nikestore.common.asyncNetworkRequest
import ir.udmx.nikestore.data.Comment
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.data.repo.CommentRepository

class ProductDetailViewModel(bundle: Bundle, commentRepository: CommentRepository) :
    NikeViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }
}