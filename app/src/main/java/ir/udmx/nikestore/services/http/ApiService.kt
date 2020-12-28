package ir.udmx.nikestore.services.http

import io.reactivex.Single
import ir.udmx.nikestore.data.Banner
import ir.udmx.nikestore.data.Product
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("product/list")
    fun getProduct(): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>
}

fun createApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}