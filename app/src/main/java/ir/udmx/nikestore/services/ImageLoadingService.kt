package ir.udmx.nikestore.services

import ir.udmx.nikestore.view.NikeImageView

interface ImageLoadingService {
    fun load(imageView: NikeImageView, imageUrl: String)
}