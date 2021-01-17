package ir.udmx.nikestore.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.udmx.nikestore.common.EXTRA_KEY_DATA
import ir.udmx.nikestore.common.NikeFragment
import ir.udmx.nikestore.common.convertDpToPixel
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.data.SORT_LATEST
import ir.udmx.nikestore.databinding.FragmentHomeBinding
import ir.udmx.nikestore.feature.common.ProductListAdapter
import ir.udmx.nikestore.feature.common.VIEW_TYPE_ROUND
import ir.udmx.nikestore.feature.list.ProductListActivity
import ir.udmx.nikestore.feature.main.BannerSliderAdapter
import ir.udmx.nikestore.feature.product.ProductDetailActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class HomeFragment : NikeFragment(), ProductListAdapter.OnProductClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val homeViewModel: HomeViewModel by viewModel()
    val productListAdapter: ProductListAdapter by inject{ parametersOf(VIEW_TYPE_ROUND)}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.latestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.latestProductsRv.adapter = productListAdapter
        productListAdapter.onProductClickListener = this

        homeViewModel.productsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        binding.viewLatestProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(),ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        homeViewModel.bannersLiveData.observe(viewLifecycleOwner) {
            Timber.i(it.toString())
            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            binding.bannerSliderViewPager.adapter = bannerSliderAdapter
            val width = binding.bannerSliderViewPager.measuredWidth
            val viewPagerHeight =
                (((width - convertDpToPixel(32f, requireContext())) * 173) / 328).toInt()
            val layoutParams = binding.bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            binding.bannerSliderViewPager.layoutParams = layoutParams
            binding.sliderIndicator.setViewPager2(binding.bannerSliderViewPager)
            Timber.i("Width ViewPager is $width")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}