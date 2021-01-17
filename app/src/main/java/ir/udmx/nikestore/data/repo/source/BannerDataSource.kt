package ir.udmx.nikestore.data.repo.source

import io.reactivex.Single
import ir.udmx.nikestore.data.Banner

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}