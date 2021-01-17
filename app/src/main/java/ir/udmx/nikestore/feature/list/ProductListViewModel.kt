package ir.udmx.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.NikeSingleObserver
import ir.udmx.nikestore.common.NikeViewModel
import ir.udmx.nikestore.common.asyncNetworkRequest
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.data.repo.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {
    val productsLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProducts()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }

    fun getProducts() {
        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })
    }

    fun onSelectedSortChangedByUser(sort: Int) {
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitles[sort]
        getProducts()
    }
}