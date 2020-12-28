package ir.udmx.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.services.http.ApiService

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {
    override fun getProducts(): Single<List<Product>> = apiService.getProduct()

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(): Completable {
        TODO("Not yet implemented")
    }
}