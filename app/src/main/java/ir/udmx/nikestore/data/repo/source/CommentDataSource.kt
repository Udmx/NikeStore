package ir.udmx.nikestore.data.repo.source

import io.reactivex.Single
import ir.udmx.nikestore.data.Comment

interface CommentDataSource {
    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}