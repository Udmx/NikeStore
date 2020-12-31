package ir.udmx.nikestore.services.http

import com.facebook.drawee.view.SimpleDraweeView
import ir.udmx.nikestore.common.NikeViewModel
import ir.udmx.nikestore.services.ImageLoadingService
import ir.udmx.nikestore.view.NikeImageView
import java.lang.IllegalStateException

class FrescoImageLoadingService : ImageLoadingService {
    override fun load(imageView: NikeImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalStateException("ImageView must be instance of SimpleDraweeView")
    }
}