package ir.udmx.nikestore.data.repo

import io.reactivex.Single
import ir.udmx.nikestore.data.AddToCartResponse
import ir.udmx.nikestore.data.CartItemCount
import ir.udmx.nikestore.data.CartResponse
import ir.udmx.nikestore.data.MessageResponse

interface CartRepository {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int): Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCartItemsCount(): Single<CartItemCount>

}