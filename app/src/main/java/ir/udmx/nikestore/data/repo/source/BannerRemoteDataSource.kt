package ir.udmx.nikestore.data.repo.source

import io.reactivex.Single
import ir.udmx.nikestore.data.Banner
import ir.udmx.nikestore.services.http.ApiService

class BannerRemoteDataSource(val apiService: ApiService) : BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}