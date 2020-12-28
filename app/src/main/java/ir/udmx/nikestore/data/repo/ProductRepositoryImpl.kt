package ir.udmx.nikestore.data.repo

import io.reactivex.Completable
import io.reactivex.Single
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.data.repo.source.ProductDataSource
import ir.udmx.nikestore.data.repo.source.ProductLocalDataSource

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override fun getProducts(): Single<List<Product>> = remoteDataSource.getProducts()

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