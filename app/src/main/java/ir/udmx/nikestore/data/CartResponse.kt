package ir.udmx.nikestore.data

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

data class PurchaseDetail(var totalPrice: Int, val shippingCost: Int, var payablePrice: Int)