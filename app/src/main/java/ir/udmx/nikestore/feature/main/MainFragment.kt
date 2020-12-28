package ir.udmx.nikestore.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.NikeFragment
import ir.udmx.nikestore.data.Product
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment() {
    val mainViewModel: MainViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressIndicator(true)
        mainViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        mainViewModel.bannersLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
        }

    }
}