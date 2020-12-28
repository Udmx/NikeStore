package ir.udmx.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.udmx.nikestore.data.Product

class ProductLocalDataSource : ProductDataSource {
    override fun getProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

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