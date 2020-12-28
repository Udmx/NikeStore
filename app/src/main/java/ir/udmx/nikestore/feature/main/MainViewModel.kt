package ir.udmx.nikestore.feature.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.udmx.nikestore.common.NikeViewModel
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.data.repo.ProductRepository
import timber.log.Timber

class MainViewModel(productRepository: ProductRepository) : NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        progressBarLiveData.value = true
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : SingleObserver<List<Product>> {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }
            })
    }
}