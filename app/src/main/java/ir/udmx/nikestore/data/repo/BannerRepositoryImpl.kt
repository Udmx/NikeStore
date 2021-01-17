package ir.udmx.nikestore.data.repo

import io.reactivex.Single
import ir.udmx.nikestore.data.Banner
import ir.udmx.nikestore.data.repo.source.BannerDataSource

class BannerRepositoryImpl(val bannerRemoteDataSource : BannerDataSource) : BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}