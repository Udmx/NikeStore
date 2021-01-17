package ir.udmx.nikestore.feature.list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.EXTRA_KEY_DATA
import ir.udmx.nikestore.common.NikeActivity
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.databinding.ActivityProductListBinding
import ir.udmx.nikestore.feature.common.ProductListAdapter
import ir.udmx.nikestore.feature.common.VIEW_TYPE_LARGE
import ir.udmx.nikestore.feature.common.VIEW_TYPE_SMALL
import ir.udmx.nikestore.feature.product.ProductDetailActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity() ,ProductListAdapter.OnProductClickListener {
    private lateinit var binding: ActivityProductListBinding
    val viewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }
    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.productsRv.layoutManager = gridLayoutManager
        binding.productsRv.adapter = productListAdapter
        productListAdapter.onProductClickListener=this
        binding.viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            } else {
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }

        viewModel.selectedSortTitleLiveData.observe(this) {
            binding.selectedSortTitleTv.text = getString(it)
        }

        viewModel.progressBarLiveData.observe(this) {
            setProgressIndicator(it)
        }

        viewModel.productsLiveData.observe(this) {
            Timber.i(it.toString())
            productListAdapter.products = it as ArrayList<Product>
        }

        binding.toolbarView.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        binding.sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitlesArray,
                    viewModel.sort
                ) { dialog, selectedSortIndex ->
                    dialog.dismiss()
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                }.setTitle(getString(R.string.sort))
            dialog.show()
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this,ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA,product)
        })
    }
}