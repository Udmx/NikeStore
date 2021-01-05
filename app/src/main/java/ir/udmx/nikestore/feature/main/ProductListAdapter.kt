package ir.udmx.nikestore.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.formatPrice
import ir.udmx.nikestore.common.implementSpringAnimationTrait
import ir.udmx.nikestore.data.Product
import ir.udmx.nikestore.services.ImageLoadingService
import ir.udmx.nikestore.view.NikeImageView

class ProductListAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    var onProductClickListener: OnProductClickListener? = null
    var products: List<Product> = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)

        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv, product.image)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                onProductClickListener?.onProductClick(product)
            }
        }
    }

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }
}