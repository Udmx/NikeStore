package ir.udmx.nikestore

import android.app.Application
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import ir.udmx.nikestore.data.repo.*
import ir.udmx.nikestore.data.repo.source.*
import ir.udmx.nikestore.feature.common.ProductListAdapter
import ir.udmx.nikestore.feature.list.ProductListViewModel
import ir.udmx.nikestore.feature.home.HomeViewModel
import ir.udmx.nikestore.feature.product.ProductDetailViewModel
import ir.udmx.nikestore.feature.product.comment.CommentListViewModel
import ir.udmx.nikestore.services.ImageLoadingService
import ir.udmx.nikestore.services.http.ApiService
import ir.udmx.nikestore.services.http.FrescoImageLoadingService
import ir.udmx.nikestore.services.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)
        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}