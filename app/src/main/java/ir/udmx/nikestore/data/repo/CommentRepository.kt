package ir.udmx.nikestore.data.repo

import io.reactivex.Single
import ir.udmx.nikestore.data.Comment

interface CommentRepository {
    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}