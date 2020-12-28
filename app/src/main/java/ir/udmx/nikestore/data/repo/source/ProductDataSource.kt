package ir.udmx.nikestore.data.repo.source

import io.reactivex.Completable
import io.reactivex.Single
import ir.udmx.nikestore.data.Product

interface ProductDataSource {
    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(): Completable

    fun deleteFromFavorites(): Completable
}