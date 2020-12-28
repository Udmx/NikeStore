package ir.udmx.nikestore

import android.app.Application
import ir.udmx.nikestore.data.repo.BannerRepository
import ir.udmx.nikestore.data.repo.BannerRepositoryImpl
import ir.udmx.nikestore.data.repo.ProductRepository
import ir.udmx.nikestore.data.repo.ProductRepositoryImpl
import ir.udmx.nikestore.data.repo.source.BannerRemoteDataSource
import ir.udmx.nikestore.data.repo.source.ProductLocalDataSource
import ir.udmx.nikestore.data.repo.source.ProductRemoteDataSource
import ir.udmx.nikestore.feature.main.MainViewModel
import ir.udmx.nikestore.services.http.ApiService
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
        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            viewModel { MainViewModel(get(), get()) }
        }
        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}