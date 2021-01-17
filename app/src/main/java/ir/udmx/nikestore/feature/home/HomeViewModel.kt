package ir.udmx.nikestore.feature.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.udmx.nikestore.common.NikeSingleObserver
import ir.udmx.nikestore.common.NikeViewModel
import ir.udmx.nikestore.data.Banner
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.data.SORT_LATEST
import ir.udmx.nikestore.data.SORT_POPULAR
import ir.udmx.nikestore.data.repo.BannerRepository
import ir.udmx.nikestore.data.repo.ProductRepository
import timber.log.Timber

class HomeViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) :
    NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val bannersLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true
        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })
        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })
    }
}