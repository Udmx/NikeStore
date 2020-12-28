package ir.udmx.nikestore.data.repo

import io.reactivex.Completable
import io.reactivex.Single
import ir.udmx.nikestore.data.Product

interface ProductRepository {
    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(): Completable

    fun deleteFromFavorites(): Completable
}