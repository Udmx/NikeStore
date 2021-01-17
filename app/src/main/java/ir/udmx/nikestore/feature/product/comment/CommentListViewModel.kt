package ir.udmx.nikestore.feature.product.comment

import androidx.lifecycle.MutableLiveData
import ir.udmx.nikestore.common.NikeSingleObserver
import ir.udmx.nikestore.common.NikeViewModel
import ir.udmx.nikestore.common.asyncNetworkRequest
import ir.udmx.nikestore.data.Comment
import ir.udmx.nikestore.data.repo.CommentRepository

class CommentListViewModel(productId: Int, commentRepository: CommentRepository) : NikeViewModel() {
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        progressBarLiveData.value = true
        commentRepository.getAll(productId)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })

    }
}