package ir.udmx.nikestore.data.repo

import io.reactivex.Single
import ir.udmx.nikestore.data.Banner

interface BannerRepository {
    fun getBanners() : Single<List<Banner>>
}